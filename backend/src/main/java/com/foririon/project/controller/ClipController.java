package com.foririon.project.controller;

import com.foririon.project.service.ClipService;
import com.foririon.project.vo.HotClipVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stream") // 공통 경로를 클래스 레벨에 지정
public class ClipController {

  @Autowired
  private ClipService clipService;

  @GetMapping("/hot-clips") // 세부 경로를 메소드 레벨에 지정
  public List<HotClipVO> getHotClips() {
    return clipService.getHotClips();
  }
}