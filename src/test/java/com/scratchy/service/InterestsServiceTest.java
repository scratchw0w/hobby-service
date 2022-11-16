package com.scratchy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import com.scratchy.entity.Interest;
import com.scratchy.repository.InterestsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InterestsServiceTest {

  @Mock
  private InterestsRepository interestsRepository;

  @InjectMocks
  private InterestsService interestsService;

  @Test
  void shouldReturnAvailableInterests() {
    // given
    var firstInterest = new Interest();
    firstInterest.setId(1);
    firstInterest.setName("First");

    var secondInterest = new Interest();
    secondInterest.setId(2);
    secondInterest.setName("Second");

    // when
    when(interestsRepository.findAll()).thenReturn(List.of(firstInterest, secondInterest));
    var result = interestsService.getAvailableInterestsList();

    // then
    assertEquals(List.of(firstInterest, secondInterest), result);
  }
}
