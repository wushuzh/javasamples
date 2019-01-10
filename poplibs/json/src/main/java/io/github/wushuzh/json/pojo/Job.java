package io.github.wushuzh.json.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Job {
  @Getter
  @Setter
  private String title;
  @Getter
  @Setter
  private double annualIncome;
  @Getter
  @Setter
  private int activeYears;
}
