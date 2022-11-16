package com.scratchy.controller;

import java.util.List;

import com.scratchy.entity.Interest;
import com.scratchy.repository.InterestsRepository;
import com.scratchy.utils.IntegrationTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;

public class InterestsControllerTest extends IntegrationTest {

  private static final String BASE_URL = "/v1/interests";

  static Interest firstInterest;
  static Interest secondInterest;

  static {
    firstInterest = new Interest();
    firstInterest.setName("Sport");

    secondInterest = new Interest();
    secondInterest.setName("Dancing");
  }

  private static final List<Interest> INTERESTS = List.of(firstInterest, secondInterest);

  @Autowired
  private WebTestClient webTestClient;
  @Autowired
  private InterestsRepository interestsRepository;

  @BeforeEach
  public void setUp() {
    interestsRepository.saveAll(INTERESTS);
  }

  @AfterEach
  public void cleanUp() {
    interestsRepository.deleteAll();
  }

  @Test
  @SneakyThrows
  void shouldReturnInterestsList() {
    webTestClient.get()
        .uri(BASE_URL)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(new ParameterizedTypeReference<List<Interest>>() {})
        .isEqualTo(INTERESTS);
  }
}
