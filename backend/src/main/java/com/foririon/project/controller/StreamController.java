package com.foririon.project.controller;

import com.foririon.project.service.StreamService;
import com.foririon.project.vo.LiveStatusContentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stream")
public class StreamController {

  @Autowired
  private StreamService testService;

  @GetMapping("/live-status")
  public LiveStatusContentVO liveStatus() {
    return testService.getLiveStatus();
  }

  @GetMapping("/is-live")
  public boolean isCurrentlyLive() {
    return testService.isCurrentlyLive();
  }
}