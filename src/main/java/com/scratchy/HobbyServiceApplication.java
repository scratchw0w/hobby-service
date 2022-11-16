package com.scratchy;

import com.scratchy.properties.HobbyAnalysisServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({HobbyAnalysisServiceProperties.class})
public class HobbyServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(HobbyServiceApplication.class, args);
  }
}
