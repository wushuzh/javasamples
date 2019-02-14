package io.github.wushuzh.core.datetime;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Demo {
  public static void main(String[] args) {

    Clock testClock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);
    LocalDate testDate = LocalDate.now(testClock);

    // create a calendar
    Calendar calendar = new Calendar();

    // add some tasks to the calendar
    calendar.addTask(1, 0, "Answer urgent e-mail");
    calendar.addTask(4, 0, "Write deployment report");
    calendar.addTask(4, 0, "Plan security configuration");

    // add some work periods to the calendar
    calendar.addWorkPeriods(Utils.generateWorkPeriods(testDate, 3));

    // add an event to the calendar, specifying its time zone
    // America/New_York -> roughly consider (without DST) Eastern Time Zone -> UTC-05:00
    // In UTC, the meeting would begin at 13:30 and last 1 hour

    ZonedDateTime meetingTime =
        ZonedDateTime.of(testDate.atTime(8, 30), ZoneId.of("America/New_York"));
    calendar.addEvent(meetingTime, Duration.ofHours(1), "Conference call with NYC");

    // create a working schedule
    Schedule schedule = calendar.createSchedule(testClock);

    // and print it out
    System.out.println(schedule);

/*
  1970-01-01
    WorkPeriod: startTime=1970-01-01T09:00, endTime=1970-01-01T12:30
      Answer urgent e-mail(1/1) PT1H
      Write deployment report(1/2) PT2H30M
    Conference call with NYC: 下午1:30, duration = 1hr 0mins
    WorkPeriod: startTime=1970-01-01T14:30, endTime=1970-01-01T17:00
      Write deployment report(2/2) PT1H30M
      Plan security configuration(1/2) PT1H
  1970-01-02
    WorkPeriod: startTime=1970-01-02T09:00, endTime=1970-01-02T12:30
      Plan security configuration(2/2) PT3H
    WorkPeriod: startTime=1970-01-02T13:30, endTime=1970-01-02T17:00
  1970-01-05
    WorkPeriod: startTime=1970-01-05T09:00, endTime=1970-01-05T12:30
    WorkPeriod: startTime=1970-01-05T13:30, endTime=1970-01-05T17:00
*/
  }
}
