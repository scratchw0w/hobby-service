package com.scratchy.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "service.hobby-analysis-service")
public class HobbyAnalysisServiceProperties {

  private String baseUrl;
  private String getHobbyListByInterestsUrl;
}
