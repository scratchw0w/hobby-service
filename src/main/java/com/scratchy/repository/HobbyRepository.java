package com.scratchy.repository;

import java.util.List;

import com.scratchy.entity.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {

  List<Hobby> findAllByNameIn(List<String> hobbyNames);
}
 
