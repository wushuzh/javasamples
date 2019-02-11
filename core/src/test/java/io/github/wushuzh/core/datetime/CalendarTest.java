package io.github.wushuzh.core.datetime;

import static org.junit.Assert.assertEquals;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CalendarTest {

  private Calendar calendar;

  private Task t20mins, t30mins;
  private WorkPeriod p20mins, p30mins;
  private LocalDateTime localSchedStart;
  private Clock clock;

  @Before
  public void setup() {
    t20mins = new Task(20, "");
    t30mins = new Task(30, "");

    calendar = new Calendar();

    LocalDate startDate = LocalDate.ofEpochDay(0);
    ZoneId theZone = ZoneOffset.UTC;
    clock = Clock.fixed(Instant.from(startDate.atStartOfDay(theZone)), theZone);
    localSchedStart = LocalDateTime.now(clock);

    p20mins = new WorkPeriod(localSchedStart, localSchedStart.plusMinutes(20));
    p30mins = new WorkPeriod(localSchedStart.plusMinutes(60), localSchedStart.plusMinutes(90));
  }

  @Test
  public void testAllocateOneTaskSuccess() {
    calendar.addTask(t20mins);
    calendar.addWorkPeriod(p30mins);

    Schedule schedule = calendar.createSchedule(clock);

    List<WorkPeriod> scheduledPeriods = schedule.getScheduledPeriods();
    assertEquals(1, scheduledPeriods.size());
    List<TaskPart> taskParts = scheduledPeriods.get(0).getTaskParts();
    assertEquals(1, taskParts.size());
    assertEquals(t20mins, taskParts.get(0).getOwner());
    assertEquals(Duration.ofMinutes(20), taskParts.get(0).getDuration());
  }

  @Test
  public void testOrderOfAllocatedTwoPeriods() {
    calendar.addWorkPeriod(p30mins);
    calendar.addWorkPeriod(p20mins);

    Schedule schedule = calendar.createSchedule(clock);

    List<WorkPeriod> scheduledPeriods = schedule.getScheduledPeriods();
    assertEquals(2, scheduledPeriods.size());

    WorkPeriod FirstWP = scheduledPeriods.get(0);
    assertEquals(localSchedStart, FirstWP.getStartTime());
    assertEquals(localSchedStart.plusMinutes(20), FirstWP.getEndTime());
    WorkPeriod SecondWP = scheduledPeriods.get(1);
    assertEquals(localSchedStart.plusHours(1), SecondWP.getStartTime());
  }

  @Test
  public void testAllocateTwoPeriodsWithSingleTask() {
    calendar.addTask(t30mins);
    calendar.addWorkPeriod(p20mins);
    calendar.addWorkPeriod(p30mins);

    List<WorkPeriod> scheduledPeriods = calendar.createSchedule(clock).getScheduledPeriods();
    assertEquals(2, scheduledPeriods.size());

    List<TaskPart> taskParts1 = scheduledPeriods.get(0).getTaskParts();
    assertEquals(1, taskParts1.size());
    assertEquals(t30mins, taskParts1.get(0).getOwner());
    assertEquals(Duration.ofMinutes(20), taskParts1.get(0).getDuration());

    List<TaskPart> taskParts2 = scheduledPeriods.get(1).getTaskParts();
    assertEquals(1, taskParts2.size());
    assertEquals(t30mins, taskParts2.get(0).getOwner());
    assertEquals(Duration.ofMinutes(10), taskParts2.get(0).getDuration());
  }
}
