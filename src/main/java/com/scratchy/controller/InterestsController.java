package com.scratchy.controller;

import java.util.List;

import com.scratchy.entity.Interest;
import com.scratchy.service.InterestsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/interests")
public class InterestsController {

  private final InterestsService interestsService;

  @Operation(description = "Retrieves all interests list")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Interests list successfully received"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  @GetMapping
  public ResponseEntity<List<Interest>> getInterestsList() {
    log.info("Getting available interests list");
    return ResponseEntity.ok(interestsService.getAvailableInterestsList());
  }
}
