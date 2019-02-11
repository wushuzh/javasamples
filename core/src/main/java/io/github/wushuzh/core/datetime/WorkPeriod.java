package io.github.wushuzh.core.datetime;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

import static java.time.temporal.ChronoUnit.DAYS;

public class WorkPeriod {

  private LocalDateTime startTime;
  private LocalDateTime endTime;

  private List<TaskPart> taskParts;

  WorkPeriod(LocalDateTime startTime, Duration d) {
    this(startTime, startTime.plus(d));
  }

  public WorkPeriod(LocalDateTime startTime, LocalDateTime endTime) {
    this(startTime, endTime, new ArrayList<>());
  }

  public WorkPeriod(LocalDateTime startTime, LocalDateTime endTime, List<TaskPart> taskParts) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.taskParts = taskParts;
    validatePeriodTimes(startTime, endTime);
  }

  private void validatePeriodTimes(LocalDateTime startTime, LocalDateTime endTime) {
    if (endTime.isAfter(startTime.truncatedTo(DAYS).plusDays(2))) {
      throw new IllegalArgumentException("Periods cannot span more than two days");
    }
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  List<TaskPart> getTaskParts() {
    return taskParts;
  }

  void setTaskParts(List<TaskPart> taskParts) {
    this.taskParts = taskParts;
  }

  public Optional<WorkPeriod> split() {
    LocalDateTime midnight = startTime.toLocalDate().plusDays(1).atStartOfDay();
    return split(midnight);
  }

  public Optional<WorkPeriod> split(LocalDateTime splitTime) {
    if (startTime.isBefore(splitTime) && ! splitTime.isAfter(endTime)) {
      // The new split WorkPeriod range is 1st half of original WorkPeriod
      // while the 2nd half (remaining) is reassign to original WorkPeriod obj, i.e., this obj
      WorkPeriod newPeriod = new WorkPeriod(startTime, Duration.between(startTime, splitTime));
      startTime = splitTime;
      if (!taskParts.isEmpty()) {
        // First reassign TaskParts in two ends, i.e., earliest and latest
        NavigableMap<LocalDateTime, TaskPart> timeToTaskPartMap = new TreeMap<>();
        LocalDateTime taskStartTime = newPeriod.getStartTime();
        for(TaskPart taskPart: taskParts) {
          timeToTaskPartMap.put(taskStartTime, taskPart);
          taskStartTime = taskStartTime.plus(taskPart.getDuration());
        }
        newPeriod.setTaskParts(new ArrayList<>(timeToTaskPartMap.headMap(splitTime).values()));
        setTaskParts(new ArrayList<>(timeToTaskPartMap.tailMap(splitTime).values()));

        // Then deal with the one TaskPart contains splitTime
        Map.Entry<LocalDateTime, TaskPart> taskPartEntry = timeToTaskPartMap.lowerEntry(splitTime);
        LocalDateTime partStartTime = taskPartEntry.getKey();
        TaskPart partToSplit = taskPartEntry.getValue();
        Duration partDuration = partToSplit.getDuration();
        if (partStartTime.isBefore(splitTime) && partStartTime.plus(partDuration).isAfter(splitTime) ) {
          // The 1st half of TaskPart will be cut to align the newly created WorkPeriod
          // The 2nd half will fit into a new TaskPart and insert to the head of remaining WorkPeriod
          TaskPart newTaskPart = partToSplit.split(Duration.between(partStartTime, splitTime));
          getTaskParts().add(0, newTaskPart);
        }
      }
      return Optional.of(newPeriod);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(endTime, startTime);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    WorkPeriod other = (WorkPeriod) obj;
    return Objects.equals(endTime, other.endTime)
        && Objects.equals(startTime, other.startTime);
  }

  @Override
  public String toString() {
    String workPeriodHeader = "\n\tWorkPeriod: startTime=" + startTime + ", endTime=" + endTime;
    StringBuilder sb = new StringBuilder(workPeriodHeader);
    for (TaskPart tp : taskParts) {
      sb.append("\n\t\t").append(tp);
    }
    return sb.toString();
  }
}
