package com.scratchy.controller;

import java.util.List;

import com.scratchy.entity.Hobby;
import com.scratchy.entity.Interest;
import com.scratchy.service.HobbyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/hobby")
public class HobbyController {

  private final HobbyService hobbyService;

  @Operation(description = "Retrieves all hobby list")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Hobby list successfully received"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  @GetMapping
  public ResponseEntity<List<Hobby>> getAllHobbyList() {
    log.info("Getting all available hobbies");
    return ResponseEntity.ok(hobbyService.getAllHobbies());
  }

  @Operation(description = "Retrieves hobby list by interest list")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Hobby list successfully received"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  @PostMapping("/with")
  public ResponseEntity<List<Hobby>> getHobbyListByInterests(@RequestBody List<Interest> interests) {
    log.info("Getting hobby list by interests: {}", interests);
    return ResponseEntity.ok(hobbyService.getHobbiesByInterests(interests));
  }
}
