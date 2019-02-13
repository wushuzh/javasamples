package io.github.wushuzh.core.datetime;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toCollection;

public class Calendar {

  private NavigableSet<WorkPeriod> workPeriods = new TreeSet<>(); // order by start time
  private NavigableSet<Event> events = new TreeSet<>(); // order by start time
  private List<Task> tasks = new ArrayList<>();

  public Calendar addTask(int hours, int minutes, String description) {
    addTask(new Task(hours, minutes, description));
    return this;
  }

  public Calendar addTask(Task task) {
    tasks.add(task);
    return this;
  }

  public Calendar addWorkPeriods(List<WorkPeriod> periods) {
    for (WorkPeriod wp : periods) {
      addWorkPeriod(wp);
    }
    return this;
  }

  public Calendar addWorkPeriod(WorkPeriod p) {
    WorkPeriod preceding = workPeriods.floor(p);
    WorkPeriod following = workPeriods.ceiling(p);
    if (preceding != null && preceding.getEndTime().isAfter(p.getStartTime())) {
      throw new IllegalArgumentException("Work Periods cannot overlap: " + preceding + "," + p);
    } else if (following != null && following.getStartTime().isBefore(p.getEndTime())) {
      throw new IllegalArgumentException("Work Periods cannot overlap: " + following + "," + p);
    }
    workPeriods.add(p);
    return this;
  }

  public Schedule createSchedule(Clock clock) {

    NavigableSet<WorkPeriod> overwrittenPeriods = overwirtePeriodsByEvents(workPeriods, events, clock.getZone());

    List<TaskPart> remainingTaskParts = tasks.stream().map(TaskPart::wholeOf).collect(toList());
    List<WorkPeriod> scheduledPeriods = new ArrayList<>();

    LocalDateTime ldt = LocalDateTime.now(clock);
    for (WorkPeriod p : overwrittenPeriods) {
      LocalDateTime effectiveStartTime = p.getStartTime().isAfter(ldt) ? p.getStartTime() : ldt;
      // TODO doesn't allow for DST changes during WorkPeriod
      if (!Duration.between(effectiveStartTime, p.getEndTime()).minus(WorkPeriod.MINIMUM_DURATION).isNegative()) {
        p.split(effectiveStartTime);
        p.setTaskParts(remainingTaskParts);
        scheduledPeriods.add(p.split(p.getEndTime()).orElseThrow(IllegalArgumentException::new));
        remainingTaskParts = p.getTaskParts();
      }
    }
    return new Schedule(clock.getZone(), scheduledPeriods, events, remainingTaskParts.isEmpty());
  }

  public Calendar addEvent(ZonedDateTime eventDateTime, Duration duration, String description) {
    addEvent(Event.of(eventDateTime, eventDateTime.plus(duration), description));
    return this;
  }

  public Calendar addEvent(Event e) {
    events.add(e);
    return this;
  }

  public NavigableSet<WorkPeriod> overwritePeriodsByEvents(ZoneId zone) {
    return overwirtePeriodsByEvents(workPeriods, events, zone);
  }

  private NavigableSet<WorkPeriod> overwirtePeriodsByEvents(NavigableSet<WorkPeriod> workPeriods,
      NavigableSet<Event> events, ZoneId zone) {
    NavigableSet<WorkPeriod> overwrittenPeriods = new TreeSet<>();

    NavigableSet<WorkPeriod> rawPeriods = workPeriods.stream()
      .map(WorkPeriod::copy)
      .collect(toCollection(TreeSet::new));

    WorkPeriod period = rawPeriods.pollFirst();
    Event event = events.isEmpty() ? null : events.first();
    while (period != null && event != null) {
      if (! period.getEndTime().isAfter(event.getLocalStartDateTime(zone))) {
        // non-overlapping, period first
        overwrittenPeriods.add(period);
        period = rawPeriods.higher(period);
      }
    }
    if (period != null) {
      overwrittenPeriods.add(period);
      overwrittenPeriods.addAll(rawPeriods.tailSet(period));
    }
    return overwrittenPeriods;
  }
}
