package io.github.wushuzh.core.datetime;

import java.time.ZoneId;
import java.util.List;

public class Schedule {

  private ZoneId zoneId;
  private List<WorkPeriod> scheduledPeriods;
  private boolean successful;

  public Schedule(ZoneId zoneId, List<WorkPeriod> scheduledPeriods, boolean success) {
    this.zoneId = zoneId;
    this.scheduledPeriods = scheduledPeriods;
    this.successful = success;
  }

  public List<WorkPeriod> getScheduledPeriods() {
    return scheduledPeriods;
  }

  public boolean isSuccessful() {
    return successful;
  }
}
