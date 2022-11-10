package com.scratchy.controller;

import java.util.List;

import com.scratchy.entity.Hobby;
import com.scratchy.service.HobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/")
public class LikedHobbyController {

  private final HobbyService hobbyService;

  @GetMapping
  public ResponseEntity<List<Hobby>> getUserHobbyList(String userId) {
    return ResponseEntity.ok(List.of());
  }
}
