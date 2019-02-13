package io.github.wushuzh.core.datetime;

import java.time.ZoneId;
import java.util.List;
import java.util.NavigableSet;

public class Schedule {

  private ZoneId zoneId;
  private List<WorkPeriod> scheduledPeriods;
  private NavigableSet<Event> events;
  private boolean successful;

  public Schedule(ZoneId zoneId, List<WorkPeriod> scheduledPeriods, NavigableSet<Event> events, boolean success) {
    this.zoneId = zoneId;
    this.scheduledPeriods = scheduledPeriods;
    this.events = events;
    this.successful = success;
  }

  public List<WorkPeriod> getScheduledPeriods() {
    return scheduledPeriods;
  }

  public boolean isSuccessful() {
    return successful;
  }
}
