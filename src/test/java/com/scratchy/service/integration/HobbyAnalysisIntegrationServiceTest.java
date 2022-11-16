package com.scratchy.service.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.scratchy.utils.TestUtils.HOBBIES;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratchy.entity.Interest;
import com.scratchy.utils.IntegrationTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HobbyAnalysisIntegrationServiceTest extends IntegrationTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private HobbyAnalysisIntegrationService hobbyAnalysisIntegrationService;

  @Test
  @SneakyThrows
  void shouldReturnHobbiesByInterestsList() {
    stubFor(get(urlEqualTo("/hobby-analysis-service/analyze"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody(objectMapper.writeValueAsString(HOBBIES))));

    var result = hobbyAnalysisIntegrationService.getHobbiesByInterests(List.of(new Interest()));

    assertNotNull(result);
    verify(getRequestedFor(urlPathEqualTo("/hobby-analysis-service/analyze")));
  }
}
