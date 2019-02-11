package io.github.wushuzh.core.datetime;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Task {

  private final Duration duration;
  private final String description;
  private final List<TaskPart> taskParts;

  public Task(int minutes, String description) {
    this(Duration.ofMinutes(minutes), description);
  }

  public Task(Duration duration, String description) {
    this.duration = duration;
    this.description = description;
    taskParts = new ArrayList<>();
  }

  public Duration getDuration() {
    return duration;
  }

  public String getDescription() {
    return description;
  }

  TaskPart createTaskPart(Duration d) {
    TaskPart t = new TaskPart(this, d, getTaskPartCount() + 1);
    taskParts.add(t);
    return t;
  }

  int getTaskPartCount() {
    return taskParts.size();
  }
}
