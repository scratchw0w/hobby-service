package com.scratchy.controller;

import java.util.List;

import com.scratchy.entity.Interest;
import com.scratchy.service.InterestsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/interests")
public class InterestsController {

  private final InterestsService interestsService;

  @GetMapping
  public ResponseEntity<List<Interest>> getInterestsList() {
    return ResponseEntity.ok(List.of());
  }
}
