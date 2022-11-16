package com.scratchy.service.integration;

import java.util.List;

import com.scratchy.entity.Hobby;
import com.scratchy.entity.Interest;
import com.scratchy.properties.HobbyAnalysisServiceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class HobbyAnalysisIntegrationService {

  private final RestTemplate restTemplate;
  private final HobbyAnalysisServiceProperties properties;

  public List<Hobby> getHobbiesByInterests(List<Interest> interests) {
    var body = new HttpEntity<>(interests);
    var url = properties.getGetHobbyListByInterestsUrl();

    log.debug("Sending request to hobby analysis service with body={}", body);
    return restTemplate.exchange(url,
            HttpMethod.GET,
            body,
            new ParameterizedTypeReference<List<Hobby>>() {})
        .getBody();
  }
}
