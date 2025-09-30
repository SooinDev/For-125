package com.foririon.project.scheduler;

import com.foririon.project.service.NotificationService;
import com.foririon.project.service.StreamService;
import com.foririon.project.vo.LiveStatusContentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StreamCheckScheduler {

  private final StreamService streamService;
  private final NotificationService notificationService;

  @Autowired
  public StreamCheckScheduler(StreamService streamService, NotificationService notificationService) {
    this.streamService = streamService;
    this.notificationService = notificationService;
  }

  @Scheduled(cron = "0 * * * * ?")
  public void checkLiveStatus() {
    LiveStatusContentVO status = streamService.getLiveStatus();

    if (status == null) {
      System.out.println("방송 상태 확인 실패: API 응답 없음");
      return;
    }

    // status 변수를 직접 사용합니다.
    boolean isLive = "OPEN".equals(status.getStatus());
    String liveId = status.getLiveId();

    System.out.println("방송 상태 확인: " + (isLive ? "ON" : "OFF"));

    if (isLive && liveId != null) {
      if (streamService.shouldSendNotification(liveId)) {
        System.out.println("새로운 방송 시작! 알림을 발송합니다. liveId: " + liveId);

        // status 객체를 직접 전달합니다.
        streamService.recordStreamStart(status);

        String title = "이리온 방송 시작!";
        String body = status.getLiveTitle();
        notificationService.sendNotification(title, body);

        streamService.markNotificationAsSent(liveId);
      }
    }
  }
}