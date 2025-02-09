package com.bank.paymentservice.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HomeController {

  @GetMapping("/home")
  public ResponseEntity<Map<String, String>> home() {
    return ResponseEntity.ok(Map.of("message", "Welcome home!"));
  }
}
