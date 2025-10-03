package com.foririon.project.controller;

import com.foririon.project.service.NotificationService;
import com.foririon.project.service.FCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private FCMService fcmService;

  @PostMapping("/register")
  public ResponseEntity<Void> registerToken(@RequestBody Map<String, String> payload) {
    String token = payload.get("token");
    if (token != null && !token.isEmpty()) {
      notificationService.registerToken(token);
    }
    return ResponseEntity.ok().build();
  }

  // FCM 토큰 저장 (Flutter에서 전송)
  @PostMapping("/token")
  public ResponseEntity<Map<String, String>> saveFCMToken(@RequestBody Map<String, String> payload) {
    String fcmToken = payload.get("fcmToken");
    System.out.println("[FCM] 토큰 수신: " + fcmToken);

    // 여기서는 단순히 로그만 출력 (필요시 DB에 저장)
    // TODO: DB에 토큰 저장 로직 추가

    Map<String, String> response = new HashMap<>();
    response.put("status", "success");
    response.put("message", "토큰이 저장되었습니다");
    return ResponseEntity.ok(response);
  }

  // 테스트용 알림 전송 (토픽)
  @PostMapping("/send")
  public ResponseEntity<Map<String, String>> sendNotification(@RequestBody Map<String, String> payload) {
    String topic = payload.get("topic");
    String title = payload.get("title");
    String body = payload.get("body");
    String type = payload.getOrDefault("type", "general");

    System.out.println("[FCM] 알림 전송 요청 - 토픽: " + topic + ", 제목: " + title);

    fcmService.sendToTopic(topic, title, body, type);

    Map<String, String> response = new HashMap<>();
    response.put("status", "success");
    response.put("message", "알림이 전송되었습니다");
    return ResponseEntity.ok(response);
  }

  // 테스트용 알림 전송 (단일 기기)
  @PostMapping("/send-to-device")
  public ResponseEntity<Map<String, String>> sendToDevice(@RequestBody Map<String, String> payload) {
    String token = payload.get("token");
    String title = payload.get("title");
    String body = payload.get("body");
    String type = payload.getOrDefault("type", "general");

    System.out.println("[FCM] 단일 기기 알림 전송 요청 - 제목: " + title);

    fcmService.sendToToken(token, title, body, type);

    Map<String, String> response = new HashMap<>();
    response.put("status", "success");
    response.put("message", "알림이 전송되었습니다");
    return ResponseEntity.ok(response);
  }
}