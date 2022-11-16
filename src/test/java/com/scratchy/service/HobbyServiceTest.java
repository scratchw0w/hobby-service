package com.scratchy.service;

import static com.scratchy.utils.TestUtils.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import com.scratchy.entity.Hobby;
import com.scratchy.entity.Interest;
import com.scratchy.entity.UserHobby;
import com.scratchy.repository.HobbyRepository;
import com.scratchy.repository.UserHobbyRepository;
import com.scratchy.service.integration.HobbyAnalysisIntegrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HobbyServiceTest {

  private final List<String> HOBBY_TITLES = List.of("Sport", "Hiking");

  @Mock
  private HobbyRepository hobbyRepository;
  @Mock
  private UserHobbyRepository userHobbyRepository;
  @Mock
  private HobbyAnalysisIntegrationService hobbyAnalysisIntegrationService;

  @InjectMocks
  private HobbyService hobbyService;

  @Test
  void shouldReturnAllHobbies() {
    // given
    var firstHobby = new Hobby();
    firstHobby.setId(1);
    firstHobby.setName("Swimming");

    var secondHobby = new Hobby();
    secondHobby.setId(2);
    secondHobby.setName("Hiking");

    // when
    when(hobbyRepository.findAll()).thenReturn(List.of(firstHobby, secondHobby));

    var result = hobbyService.getAllHobbies();

    // then
    assertEquals(List.of(firstHobby, secondHobby), result);
  }

  @Test
  void shouldReturnHobbiesByInterests() {
    // given
    var firstHobby = new Hobby();
    firstHobby.setId(1);
    firstHobby.setName("Swimming");
    var secondHobby = new Hobby();
    secondHobby.setId(2);
    secondHobby.setName("Hiking");

    var firstInterest = new Interest();
    firstInterest.setId(1);
    firstInterest.setName("Playing");
    var secondInterest = new Interest();
    secondInterest.setId(2);
    secondInterest.setName("Sport");

    // when
    when(hobbyAnalysisIntegrationService.getHobbiesByInterests(List.of(firstInterest, secondInterest)))
        .thenReturn(List.of(firstHobby, secondHobby));

    var result = hobbyService.getHobbiesByInterests(List.of(firstInterest, secondInterest));

    // then
    assertEquals(List.of(firstHobby, secondHobby), result);
  }

  @Test
  void shouldReturnUserHobbies() {
    // given
    var firstHobby = new UserHobby();
    firstHobby.setId(1);
    firstHobby.setHobbyId(0);
    firstHobby.setUserId(USER_ID);

    var secondHobby = new UserHobby();
    secondHobby.setId(2);
    secondHobby.setHobbyId(1);
    secondHobby.setUserId(USER_ID);

    // when
    when(userHobbyRepository.findAllByUserId(USER_ID)).thenReturn(List.of(firstHobby, secondHobby));

    var result = hobbyService.getUserHobbies(USER_ID);

    // then
    assertEquals(List.of(firstHobby, secondHobby), result);
  }

  @Test
  void shouldAddUserHobby() {
    // given
    var firstHobby = new Hobby();
    firstHobby.setId(1);
    firstHobby.setName("Hiking");

    var secondHobby = new Hobby();
    secondHobby.setId(2);
    secondHobby.setName("Swimming");

    // when
    when(hobbyRepository.findAllByNameIn(HOBBY_TITLES)).thenReturn(List.of(firstHobby, secondHobby));

    var result = hobbyService.addUserHobbies(USER_ID, HOBBY_TITLES);

    // then
    assertEquals(List.of(firstHobby, secondHobby), result);
  }

  @Test
  void shouldThrowIllegalArgumentExceptionForAddingInvalidHobbyTitle() {
    // given when
    when(hobbyRepository.findAllByNameIn(List.of("Not Present"))).thenReturn(Collections.emptyList());

    // then
    verifyNoInteractions(userHobbyRepository);
    assertThrows(IllegalArgumentException.class,
        () -> hobbyService.addUserHobbies(USER_ID, List.of("Not Present")));
  }

  @Test
  void shouldDeleteUserHobby() {
    // given when
    hobbyService.removeUserHobbies(USER_ID, HOBBY_TITLES);

    // then
    verify(hobbyRepository, times(1)).findAllByNameIn(HOBBY_TITLES);
    verify(userHobbyRepository, times(1)).deleteAllByUserIdAndHobbyIdIn(eq(USER_ID), anyList());
  }
}
