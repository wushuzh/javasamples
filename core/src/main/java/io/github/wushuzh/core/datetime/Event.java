package io.github.wushuzh.core.datetime;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Event implements Comparable<Event> {
  private ZonedDateTime startTime;
  private ZonedDateTime endTime;
  private String description;

  public Event(ZonedDateTime startTime, Duration duration, String description) {
    this(startTime, startTime.plus(duration), description);
  }

  public Event(ZonedDateTime startTime, ZonedDateTime endTime, String description) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.description = description;
  }

  public static Event of(ZonedDateTime startTime, ZonedDateTime endTime, String description) {
    return new Event(startTime, endTime, description);
  }

  @Override
  public int compareTo(Event e) {
    return startTime.compareTo(e.startTime);
  }

  public LocalDateTime getLocalStartDateTime(ZoneId zone) {
    return startTime.withZoneSameInstant(zone).toLocalDateTime();
  }

}