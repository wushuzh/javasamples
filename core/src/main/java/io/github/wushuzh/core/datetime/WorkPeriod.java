package io.github.wushuzh.core.datetime;

import java.time.Duration;
import java.time.LocalDateTime;

public class WorkPeriod {

  private LocalDateTime startTime;
  private LocalDateTime endTime;

  WorkPeriod(LocalDateTime startTime, Duration d) {
    this(startTime, startTime.plus(d));
  }

  public WorkPeriod(LocalDateTime startTime, LocalDateTime endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }
}
