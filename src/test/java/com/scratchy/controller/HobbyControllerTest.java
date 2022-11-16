package com.scratchy.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.scratchy.utils.TestUtils.HOBBIES;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratchy.entity.Hobby;
import com.scratchy.entity.Interest;
import com.scratchy.repository.HobbyRepository;
import com.scratchy.utils.IntegrationTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

public class HobbyControllerTest extends IntegrationTest {

  private static final String BASE_URL = "/v1/hobby";

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private WebTestClient webTestClient;
  @Autowired
  private HobbyRepository hobbyRepository;

  @BeforeEach
  public void setUp() {
    hobbyRepository.saveAll(HOBBIES);
  }

  @AfterEach
  public void cleanUp() {
    hobbyRepository.deleteAll();
  }

  @Test
  @SneakyThrows
  void shouldReturnAllHobbies() {
    webTestClient.get()
        .uri(BASE_URL)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(new ParameterizedTypeReference<List<Hobby>>() {})
        .isEqualTo(HOBBIES);
  }

  @Test
  @SneakyThrows
  void shouldReturnHobbiesByInterests() {
    stubFor(get(urlEqualTo("/hobby-analysis-service/analyze"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody(objectMapper.writeValueAsString(HOBBIES))));

    webTestClient.post()
        .uri(BASE_URL + "/with")
        .body(Mono.just(List.of(new Interest())), new ParameterizedTypeReference<>() {})
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(new ParameterizedTypeReference<List<Hobby>>() {})
        .isEqualTo(HOBBIES);
  }
}
