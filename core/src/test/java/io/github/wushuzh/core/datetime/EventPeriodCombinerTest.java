package io.github.wushuzh.core.datetime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.NavigableSet;

import org.junit.Before;
import org.junit.Test;

public class EventPeriodCombinerTest {
  private Clock clock;
  private Calendar calendar;
  private ZonedDateTime startZDateTime;
  private LocalDate startLocalDate;

  @Before
  public void setup() {
    calendar = new Calendar();
    clock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);
    startZDateTime = ZonedDateTime.now(clock);
    startLocalDate = LocalDate.from(startZDateTime);
  }

  @Test
  public void testNoWorkPeriods() {
    calendar.addEvent(Event.of(startZDateTime, startZDateTime.plusHours(1), ""));
    NavigableSet<WorkPeriod> combined = calendar.overwritePeriodsByEvents(clock.getZone());

    assertTrue(combined.isEmpty());
  }

  @Test
  public void testNoOverlapPeriodFirst() {
    calendar.addEvent(Event.of(startZDateTime.withHour(3), startZDateTime.withHour(4), ""));
    WorkPeriod period = new WorkPeriod(startLocalDate.atTime(1, 0), startLocalDate.atTime(2, 0));
    calendar.addWorkPeriod(period);

    NavigableSet<WorkPeriod> combined = calendar.overwritePeriodsByEvents(clock.getZone());

    assertEquals(1, combined.size());
    WorkPeriod p = combined.first();
    assertEquals(period.getStartTime(), p.getStartTime());
    assertEquals(period.getEndTime(), p.getEndTime());
  }
}