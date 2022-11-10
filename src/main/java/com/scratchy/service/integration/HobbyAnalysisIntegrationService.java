package com.scratchy.service.integration;

import java.util.List;

import com.scratchy.entity.Hobby;
import com.scratchy.entity.Interest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class HobbyAnalysisIntegrationService {

  private final RestTemplate restTemplate;

  public List<Hobby> getHobbiesByInterests(List<Interest> interests) {


    return null;
  }
}
