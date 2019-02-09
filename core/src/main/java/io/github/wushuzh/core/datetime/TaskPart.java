package io.github.wushuzh.core.datetime;

import java.time.Duration;

public class TaskPart {
  private final Task owner;
  private Duration duration;
  private final int partSequenceNumber;

  public TaskPart(Task owner, Duration duration, int partSequenceNumber) {
    this.owner = owner;
    this.duration = duration;
    this.partSequenceNumber = partSequenceNumber;
  }

  public static TaskPart wholeOf(Task t) {
    return t.createTaskPart(t.getDuration());
  }

  @Override
  public String toString() {
    return owner.getDescription()
        + "(" + partSequenceNumber + "/" + owner.getTaskPartCount() + ") "
        + duration;
  }
}
