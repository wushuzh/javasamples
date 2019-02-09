package io.github.wushuzh.core.datetime;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
  }

  public LocalDateTime getStartTime() {
    return startTime;
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

  private Optional<WorkPeriod> split(LocalDateTime splitTime) {
    if (startTime.isBefore(splitTime) && splitTime.isBefore(endTime)) {
      WorkPeriod newPeriod = new WorkPeriod(startTime, Duration.between(startTime, splitTime));
      startTime = splitTime;
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
