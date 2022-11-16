package com.scratchy.service;

import java.util.List;
import javax.transaction.Transactional;

import com.scratchy.entity.Hobby;
import com.scratchy.entity.Interest;
import com.scratchy.entity.UserHobby;
import com.scratchy.repository.HobbyRepository;
import com.scratchy.repository.UserHobbyRepository;
import com.scratchy.service.integration.HobbyAnalysisIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HobbyService {

  private final HobbyRepository hobbyRepository;
  private final UserHobbyRepository userHobbyRepository;
  private final HobbyAnalysisIntegrationService hobbyAnalysisService;

  public List<Hobby> getAllHobbies() {
    log.debug("Getting all available hobbies");
    return hobbyRepository.findAll();
  }

  public List<Hobby> getHobbiesByInterests(List<Interest> interests) {
    var hobby = hobbyAnalysisService.getHobbiesByInterests(interests);
    log.debug("Getting hobby with hobbyId={}", hobby.stream()
        .map(Hobby::getId)
        .toList());

    return hobby;
  }

  public List<UserHobby> getUserHobbies(String userId) {
    log.debug("Getting hobbies for user with userId={}", userId);
    return userHobbyRepository.findAllByUserId(userId);
  }

  @Transactional
  public List<Hobby> addUserHobbies(String userId, List<String> hobbyTitles) {
    var likedHobbies = hobbyRepository.findAllByNameIn(hobbyTitles);
    if (likedHobbies.isEmpty()) {
      throw new IllegalArgumentException("There is no hobbies with titles: " + hobbyTitles);
    }

    var userHobbies = likedHobbies.stream()
        .map(hobby -> hobby.mapToUserHobby(userId))
        .toList();

    log.debug("Saving hobbies: {}, for user with userId={}", userHobbies, userId);
    userHobbyRepository.saveAll(userHobbies);
    return likedHobbies;
  }

  @Transactional
  public void removeUserHobbies(String userId, List<String> hobbyTitles) {
    var hobbiesToRemove = hobbyRepository.findAllByNameIn(hobbyTitles);
    var hobbiesIds = hobbiesToRemove.stream()
        .map(Hobby::getId)
        .toList();

    log.debug("Removing hobbies with hobbyIds={} for user with userId={}", hobbiesIds, userId);
    userHobbyRepository.deleteAllByUserIdAndHobbyIdIn(userId, hobbiesIds);
  }
}
