package com.foririon.project.service.impl;

import com.foririon.project.service.ClipService;
import com.foririon.project.vo.ChzzkApiResponseVO;
import com.foririon.project.vo.HotClipContentVO;
import com.foririon.project.vo.HotClipVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class ClipServiceImpl implements ClipService {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${chzzk.channel.id}")
  private String channelId;

  @Override
  public List<HotClipVO> getHotClips() {
    String url = "https://api.chzzk.naver.com/service/v1/channels/" + channelId + "/videos?sortType=LATEST&pageSize=10";

    try {
      System.out.println("[INFO] 최근 30일 다시보기 조회 URL: " + url);

      // 'var' 대신 전체 타입을 명시적으로 작성합니다.
      ParameterizedTypeReference<ChzzkApiResponseVO<HotClipContentVO>> responseType =
              new ParameterizedTypeReference<ChzzkApiResponseVO<HotClipContentVO>>() {};

      // 원본 JSON 응답 로깅을 위해 String으로도 받아보기
      ResponseEntity<String> rawResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
      System.out.println("[DEBUG] 치지직 API 원본 응답 (첫 500자): " + rawResponse.getBody().substring(0, Math.min(500, rawResponse.getBody().length())));

      ResponseEntity<ChzzkApiResponseVO<HotClipContentVO>> responseEntity =
              restTemplate.exchange(url, HttpMethod.GET, null, responseType);

      ChzzkApiResponseVO<HotClipContentVO> response = responseEntity.getBody();

      if (response != null && response.getContent() != null && response.getContent().getData() != null) {
        System.out.println("[SUCCESS] 다시보기 " + response.getContent().getData().size() + "개 조회 성공");

        // 최근 30일 영상만 필터링
        java.time.LocalDateTime thirtyDaysAgo = java.time.LocalDateTime.now().minusDays(30);
        java.util.List<HotClipVO> filteredClips = response.getContent().getData().stream()
                .filter(clip -> {
                  if (clip.getPublishDateAt() == null || clip.getPublishDateAt().isEmpty()) {
                    return false;
                  }
                  try {
                    // publishDateAt이 "2024-11-04 16:04:32" 형식
                    java.time.LocalDateTime publishDate = java.time.LocalDateTime.parse(
                            clip.getPublishDateAt(),
                            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    );
                    return publishDate.isAfter(thirtyDaysAgo);
                  } catch (Exception e) {
                    System.err.println("[WARN] 날짜 파싱 실패: " + clip.getPublishDateAt());
                    return false;
                  }
                })
                .collect(java.util.stream.Collectors.toList());

        System.out.println("[INFO] 최근 30일 영상 필터링 결과: " + filteredClips.size() + "개");

        return filteredClips;
      }
    } catch (Exception e) {
      System.err.println("[ERROR] 다시보기 조회 실패: " + e.getMessage());
      e.printStackTrace();
    }
    return Collections.emptyList();
  }
}