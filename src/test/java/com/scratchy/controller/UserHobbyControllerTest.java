package com.scratchy.controller;

import static com.scratchy.utils.TestUtils.HOBBIES;
import static com.scratchy.utils.TestUtils.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratchy.entity.Hobby;
import com.scratchy.entity.UserHobby;
import com.scratchy.repository.HobbyRepository;
import com.scratchy.repository.UserHobbyRepository;
import com.scratchy.utils.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

public class UserHobbyControllerTest extends IntegrationTest {

  private static final String BASE_URL = "/v1/wishlist";

  static UserHobby userHobby;

  static {
    userHobby = new UserHobby();
    userHobby.setId(1);
    userHobby.setUserId(USER_ID);
    userHobby.setHobbyId(1);
  }

  private static final List<UserHobby> USER_HOBBIES = List.of(userHobby);

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private WebTestClient webTestClient;
  @Autowired
  private HobbyRepository hobbyRepository;
  @Autowired
  private UserHobbyRepository userHobbyRepository;

  @BeforeEach
  public void setUp() {
    hobbyRepository.saveAll(HOBBIES);
    userHobbyRepository.saveAll(USER_HOBBIES);
  }

  @AfterEach
  public void cleanUp() {
    hobbyRepository.deleteAll();
    userHobbyRepository.deleteAll();
  }

  @Test
  void shouldReturnAllUserHobbies() {
    webTestClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(BASE_URL)
            .queryParam("userId", USER_ID)
            .build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(new ParameterizedTypeReference<List<UserHobby>>() {})
        .isEqualTo(USER_HOBBIES);
  }

  @Test
  void shouldAddLikedHobbiesToUserWishlist() {
    webTestClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(BASE_URL)
            .queryParam("userId", USER_ID)
            .build())
        .body(Mono.just(List.of("Hiking")), new ParameterizedTypeReference<>() {})
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(new ParameterizedTypeReference<List<Hobby>>() {})
        .value(res -> assertEquals("Hiking", res.get(0).getName()));

    var result = userHobbyRepository.findAllByUserId(USER_ID);
    var hikingId = hobbyRepository.findAllByNameIn(Collections.singletonList("Hiking")).stream()
        .filter(hobby -> "Hiking".equals(hobby.getName()))
        .findAny()
        .orElseThrow(IllegalArgumentException::new)
        .getId();

    assertEquals(result.size(), 2);
    assertTrue(result.stream().anyMatch(hobby -> hobby.getHobbyId() == hikingId));
  }

  @Test
  void shouldRemoveLikedHobbiesFromUserWishlist() {
    var swimmingId = hobbyRepository.findAllByNameIn(List.of("Swimming")).get(0).getId();
    var userHobbyToRemove = new UserHobby();
    userHobbyToRemove.setId(1);
    userHobbyToRemove.setUserId(USER_ID);
    userHobbyToRemove.setHobbyId(swimmingId);

    userHobbyRepository.deleteAll();
    userHobbyRepository.save(userHobbyToRemove);

    webTestClient.method(HttpMethod.DELETE)
        .uri(uriBuilder -> uriBuilder
            .path(BASE_URL)
            .queryParam("userId", USER_ID)
            .build())
        .body(Mono.just(List.of("Swimming")), new ParameterizedTypeReference<>() {})
        .exchange()
        .expectStatus()
        .isNoContent();

    var result = userHobbyRepository.findAllByUserId(USER_ID);
    assertEquals(result.size(), 0);
  }
}
