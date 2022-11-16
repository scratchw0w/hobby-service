package com.scratchy.controller;

import java.util.List;

import com.scratchy.entity.Hobby;
import com.scratchy.entity.UserHobby;
import com.scratchy.service.HobbyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/wishlist")
public class UserHobbyController {

  private final HobbyService hobbyService;

  @GetMapping
  @Operation(description = "Retrieves user hobby list by userId")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User hobby list successfully received"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  public ResponseEntity<List<UserHobby>> getUserHobbyList(@RequestParam String userId) {
    log.info("Retrieving user hobby list for user with userId={}", userId);
    return ResponseEntity.ok(hobbyService.getUserHobbies(userId));
  }

  @Operation(description = "Add user hobby to wishlist by userId")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User hobby was successfully added"),
      @ApiResponse(responseCode = "404", description = "Hobby to add was not found"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  @PostMapping
  public ResponseEntity<List<Hobby>> likeHobby(@RequestParam String userId, @RequestBody List<String> titles) {
    log.info("Adding hobby with titles {} to user's wishlist with userId={}", titles, userId);
    var likedHobbies = hobbyService.addUserHobbies(userId, titles);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(likedHobbies);
  }

  @Operation(description = "Deletes user hobby in wishlist by userId and hobby titles")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User hobby list successfully received"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  @DeleteMapping
  public ResponseEntity<Object> dislikeHobby(@RequestParam String userId, @RequestBody List<String> titles) {
    log.info("Removing hobby with titles {} from user's wishlist with userId={}", titles, userId);
    hobbyService.removeUserHobbies(userId, titles);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
