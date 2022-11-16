package com.scratchy.service;

import java.util.List;

import com.scratchy.entity.Interest;
import com.scratchy.repository.InterestsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterestsService {

  private final InterestsRepository interestsRepository;

  public List<Interest> getAvailableInterestsList() {
    log.debug("Getting all available interests");
    return interestsRepository.findAll();
  }
}
