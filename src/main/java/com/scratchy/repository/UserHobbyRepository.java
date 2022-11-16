package com.scratchy.repository;

import java.util.List;

import com.scratchy.entity.UserHobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHobbyRepository extends JpaRepository<UserHobby, Long> {

  List<UserHobby> findAllByUserId(String userId);

  void deleteAllByUserIdAndHobbyIdIn(String userId, List<Long> hobbiesIds);
}
