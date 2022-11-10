package com.scratchy.controller;

import java.util.List;

import com.scratchy.entity.Hobby;
import com.scratchy.service.HobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/hobby")
public class HobbyController {

  private final HobbyService hobbyService;

  @GetMapping
  public ResponseEntity<List<Hobby>> getAllHobbyList() {
    return ResponseEntity.ok(List.of());
  }

  @PostMapping("/with")
  public ResponseEntity<List<Hobby>> getHobbyListByInterests(@RequestBody List<String> interests) {
    return ResponseEntity.ok(List.of());
  }
}
