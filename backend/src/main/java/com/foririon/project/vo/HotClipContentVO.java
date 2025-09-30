package com.foririon.project.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HotClipContentVO {
  private List<HotClipVO> data;

  public List<HotClipVO> getData() { return data; }
  public void setData(List<HotClipVO> data) { this.data = data; }
}