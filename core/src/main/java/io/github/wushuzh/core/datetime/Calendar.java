package io.github.wushuzh.core.datetime;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import static java.util.stream.Collectors.toList;

public class Calendar {

  private NavigableSet<WorkPeriod> workPeriods = new TreeSet<>(); // order by start time
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
    workPeriods.add(p);
    return this;
  }

  public Schedule createSchedule(Clock clock) {
    List<TaskPart> remainingTaskParts = tasks.stream().map(TaskPart::wholeOf).collect(toList());
    List<WorkPeriod> scheduledPeriods = new ArrayList<>();
    for (WorkPeriod p : workPeriods) {
      p.setTaskParts(remainingTaskParts);
      scheduledPeriods.add(p.split(p.getEndTime()).orElseThrow(IllegalArgumentException::new));
      remainingTaskParts = p.getTaskParts();
    }
    return new Schedule(clock.getZone(), scheduledPeriods, remainingTaskParts.isEmpty());
  }
}
