package com.scratchy.controller;

import java.util.List;

import com.scratchy.entity.Hobby;
import com.scratchy.service.HobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/wishlist")
public class LikedHobbyController {

  private final HobbyService hobbyService;

  @GetMapping
  public ResponseEntity<List<Hobby>> getUserHobbyList(String userId) {
    return ResponseEntity.ok(List.of());
  }

  @PostMapping
  public ResponseEntity<Hobby> likeHobby(Hobby hobby, String userId) {
    return ResponseEntity.ok(hobby);
  }

  @DeleteMapping
  public ResponseEntity<Hobby> dislikeHobby(Hobby hobby, String userId) {
    return ResponseEntity.ok(hobby);
  }
}
