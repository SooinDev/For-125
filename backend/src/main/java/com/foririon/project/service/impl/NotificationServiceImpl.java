package com.foririon.project.service.impl;

import com.foririon.project.mapper.NotificationMapper;
import com.foririon.project.service.NotificationService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

  @Autowired
  private NotificationMapper notificationMapper;

  @Override
  @Transactional
  public void registerToken(String fcmToken) {
    notificationMapper.insertToken(fcmToken);
  }

  public void sendNotification(String title, String body) {
    List<String> tokens = notificationMapper.getAllTokens(); // DB에서 모든 토큰을 가져옵니다.

    if (tokens.isEmpty()) {
      System.out.println("알림을 보낼 기기가 없습니다.");
      return;
    }

    System.out.println(tokens.size() + "개의 기기에 알림을 보냅니다.");

    for (String token : tokens) {
      Message message = Message.builder()
              .setNotification(Notification.builder()
                      .setTitle(title)
                      .setBody(body)
                      .build())
              .setToken(token)
              .build();

      FirebaseMessaging.getInstance().sendAsync(message);
    }
  }
}