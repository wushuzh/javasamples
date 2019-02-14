package io.github.wushuzh.core.datetime;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

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

  public LocalDateTime getLocalEndDateTime(ZoneId zone) {
    return endTime.withZoneSameInstant(zone).toLocalDateTime();
  }

  static Event copy(Event e) {
    return Event.of(e.startTime, e.endTime, e.description);
  }

  public Optional<Event> split(ZoneId zone) {
    LocalDateTime midnight = getLocalStartDateTime(zone).plusDays(1).truncatedTo(DAYS);
    return split(midnight.atZone(zone));
  }

  private Optional<Event> split(ZonedDateTime splitTime) {
    if (!splitTime.isAfter(startTime) || !splitTime.isBefore(endTime)) {
      return Optional.empty();
    }
    endTime = splitTime;
    Event e1 = new Event(splitTime, Duration.between(startTime, splitTime), description);
    return Optional.of(e1);
  }

  public String toString(ZoneId zone) {
    Duration dur = Duration.between(startTime, endTime);
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    return "\n\t" + description +": " + timeFormatter.format(startTime.withZoneSameInstant(zone)) + ", duration = " + Utils.formatDuration(dur);
  }
}