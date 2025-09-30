package com.foririon.project.controller;

import com.foririon.project.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

  @PostMapping("/register")
  public ResponseEntity<Void> registerToken(@RequestBody Map<String, String> payload) {
    String token = payload.get("token");
    if (token != null && !token.isEmpty()) {
      notificationService.registerToken(token);
    }
    return ResponseEntity.ok().build();
  }
}