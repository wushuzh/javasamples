package io.github.wushuzh.core.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.NavigableMap;
import java.util.TreeMap;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.joining;

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

  @Override
  public String toString() {
    if (!successful) return "Schedule unsuccessful: insufficent time for tasks";

    List<WorkPeriod> printablePeriods = scheduledPeriods.stream().map(WorkPeriod::copy).collect(toList());
    List<WorkPeriod> periodsSplitByMidnight = printablePeriods.stream()
      .map(WorkPeriod::split)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .collect(toList());

    printablePeriods.addAll(periodsSplitByMidnight);

    NavigableMap<LocalDateTime, String> dateTimeToPeriodOutput = printablePeriods.stream()
      .collect(groupingBy(WorkPeriod::getStartTime, TreeMap::new, mapping(WorkPeriod::toString, joining())));

    NavigableMap<LocalDate, String> dateToCalendarObjectOutput = dateTimeToPeriodOutput.entrySet().stream()
      .collect(groupingBy(e -> e.getKey().toLocalDate(), TreeMap::new, mapping(Map.Entry::getValue, joining())));

    StringBuilder sb = new StringBuilder();
    for (LocalDate date : dateToCalendarObjectOutput.keySet()) {
      sb.append("\n");
      sb.append(date);
      sb.append(dateToCalendarObjectOutput.get(date));
    }
    return sb.toString();
  }
}
