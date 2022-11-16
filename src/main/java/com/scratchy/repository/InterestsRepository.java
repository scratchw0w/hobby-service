package com.scratchy.repository;

import com.scratchy.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestsRepository extends JpaRepository<Interest, Long> {
}
