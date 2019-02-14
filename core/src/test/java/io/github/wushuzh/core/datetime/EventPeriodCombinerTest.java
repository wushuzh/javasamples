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

  @Test
  public void testNoOverlapEventFirst() {
    calendar.addEvent(Event.of(startZDateTime.withHour(1), startZDateTime.withHour(2), ""));
    WorkPeriod period = new WorkPeriod(startLocalDate.atTime(3, 0), startLocalDate.atTime(4, 0));
    calendar.addWorkPeriod(period);

    NavigableSet<WorkPeriod> combined = calendar.overwritePeriodsByEvents(clock.getZone());

    assertEquals(1, combined.size());
    WorkPeriod p = combined.first();
    assertEquals(period.getStartTime(), p.getStartTime());
    assertEquals(period.getEndTime(), p.getEndTime());
  }

  @Test
  public void testSimpleOverlapPeriodFirst() {
    WorkPeriod period = new WorkPeriod(startLocalDate.atTime(1, 0), startLocalDate.atTime(3, 0));
    calendar.addWorkPeriod(period);
    Event event = Event.of(startZDateTime.withHour(2), startZDateTime.withHour(4), "");
    calendar.addEvent(event);

    NavigableSet<WorkPeriod> combined = calendar.overwritePeriodsByEvents(clock.getZone());

    assertEquals(1, combined.size());
    WorkPeriod p = combined.first();
    assertEquals(period.getStartTime(), p.getStartTime());
    assertEquals(startZDateTime.withHour(2).toLocalDateTime(), p.getEndTime());
  }

  @Test
  public void testSimpleOverlapEventFirst() {
    calendar.addEvent(Event.of(startZDateTime.withHour(1), startZDateTime.withHour(3), ""));
    calendar.addWorkPeriod(new WorkPeriod(startLocalDate.atTime(2, 0), startLocalDate.atTime(4, 0)));

    NavigableSet<WorkPeriod> combined = calendar.overwritePeriodsByEvents(clock.getZone());

    assertEquals(1, combined.size());
    WorkPeriod p = combined.first();
    assertEquals(startZDateTime.withHour(3).toLocalDateTime(), p.getStartTime());
    assertEquals(startLocalDate.atTime(4, 0), p.getEndTime());
  }

  @Test
  public void testPeriodSurroundsEvent() {
    calendar.addEvent(Event.of(startZDateTime.withHour(2), startZDateTime.withHour(3), ""));
    calendar.addWorkPeriod(new WorkPeriod(startLocalDate.atTime(1, 0), startLocalDate.atTime(4, 0)));

    NavigableSet<WorkPeriod> combined = calendar.overwritePeriodsByEvents(clock.getZone());

    assertEquals(2, combined.size());
    WorkPeriod p = combined.pollFirst();
    assertEquals(startLocalDate.atTime(1, 0), p.getStartTime());
    assertEquals(startZDateTime.withHour(2).toLocalDateTime(), p.getEndTime());
    p = combined.pollFirst();
    assertEquals(startZDateTime.withHour(3).toLocalDateTime(), p.getStartTime());
    assertEquals(startLocalDate.atTime(4, 0), p.getEndTime());
  }

  @Test
  public void testEventSurroundPeriod() {
    calendar.addEvent(Event.of(startZDateTime.withHour(1), startZDateTime.withHour(4), ""));
    calendar.addWorkPeriod(new WorkPeriod(startLocalDate.atTime(2, 0), startLocalDate.atTime(3, 0)));

    NavigableSet<WorkPeriod> combined = calendar.overwritePeriodsByEvents(clock.getZone());

    assertTrue(combined.isEmpty());
  }

  @Test
  public void testSimultaneousStartEventLonger() {
    calendar.addEvent(Event.of(startZDateTime.withHour(1), startZDateTime.withHour(3), ""));
    calendar.addWorkPeriod(new WorkPeriod(startLocalDate.atTime(1, 0), startLocalDate.atTime(2, 0)));

    NavigableSet<WorkPeriod> combined = calendar.overwritePeriodsByEvents(clock.getZone());
    assertTrue(combined.isEmpty());
  }

  @Test
  public void testSimultaneousStartPeriodLonger() {
    calendar.addEvent(Event.of(startZDateTime.withHour(1), startZDateTime.withHour(2), ""));
    WorkPeriod period = new WorkPeriod(startLocalDate.atTime(1, 0), startLocalDate.atTime(3, 0));
    calendar.addWorkPeriod(period);

    NavigableSet<WorkPeriod> combined = calendar.overwritePeriodsByEvents(clock.getZone());
    assertEquals(1, combined.size());
    WorkPeriod p = combined.pollFirst();
    assertEquals(startZDateTime.withHour(2).toLocalDateTime(), p.getStartTime());
    assertEquals(startLocalDate.atTime(3, 0), p.getEndTime());
  }

  @Test
  public void testSimultaneousEndEventLonger() {
    calendar.addEvent(Event.of(startZDateTime.withHour(1), startZDateTime.withHour(4), ""));
    calendar.addWorkPeriod(new WorkPeriod(startLocalDate.atTime(2, 0), startLocalDate.atTime(4, 0)));
    
    NavigableSet<WorkPeriod> combined = calendar.overwritePeriodsByEvents(clock.getZone());
    assertTrue(combined.isEmpty());
  }

  @Test
  public void testSimultaneousEndPeriodLonger() {
    calendar.addEvent(Event.of(startZDateTime.withHour(3), startZDateTime.withHour(4), ""));
    WorkPeriod period = new WorkPeriod(startLocalDate.atTime(1, 0), startLocalDate.atTime(4, 0));
    calendar.addWorkPeriod(period);

    NavigableSet<WorkPeriod> combined = calendar.overwritePeriodsByEvents(clock.getZone());
    assertEquals(1, combined.size());
    WorkPeriod p = combined.pollFirst();
    assertEquals(startLocalDate.atTime(1, 0), p.getStartTime());
    assertEquals(startZDateTime.withHour(3).toLocalDateTime(), p.getEndTime());
  }

  @Test
  public void testAbuttingPeriodFirst() {
    calendar.addWorkPeriod(new WorkPeriod(startLocalDate.atTime(1, 0), startLocalDate.atTime(2, 0)));
    calendar.addEvent(Event.of(startZDateTime.withHour(2), startZDateTime.withHour(3), ""));
    NavigableSet<WorkPeriod> combined = calendar.overwritePeriodsByEvents(clock.getZone());

    assertEquals(1, combined.size());
    WorkPeriod p = combined.pollFirst();
    assertEquals(startLocalDate.atTime(1, 0), p.getStartTime());
    assertEquals(startLocalDate.atTime(2, 0), p.getEndTime());
  }

  @Test
  public void testAbuttingEventFirst() {
    calendar.addEvent(Event.of(startZDateTime.withHour(1), startZDateTime.withHour(2), ""));
    calendar.addWorkPeriod(new WorkPeriod(startLocalDate.atTime(2, 0), startLocalDate.atTime(3, 0)));
    NavigableSet<WorkPeriod> combined = calendar.overwritePeriodsByEvents(clock.getZone());

    assertEquals(1, combined.size());
    WorkPeriod p = combined.pollFirst();
    assertEquals(startLocalDate.atTime(2, 0), p.getStartTime());
    assertEquals(startLocalDate.atTime(3, 0), p.getEndTime());
  }
}