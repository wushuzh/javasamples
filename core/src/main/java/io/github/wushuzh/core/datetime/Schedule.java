package io.github.wushuzh.core.datetime;

import java.time.ZoneId;
import java.util.List;

public class Schedule {

  private ZoneId zoneId;
  private List<WorkPeriod> scheduledPeriods;

  public Schedule(ZoneId zoneId, List<WorkPeriod> scheduledPeriods) {
    this.zoneId = zoneId;
    this.scheduledPeriods = scheduledPeriods;
  }

  public List<WorkPeriod> getScheduledPeriods() {
    return scheduledPeriods;
  }
}
