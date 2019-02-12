package io.github.wushuzh.core.datetime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

  private Task t20mins, t30mins, t60mins;
  private WorkPeriod p20mins, p30mins, p60mins;
  private LocalDateTime localSchedStart;
  private Clock clock;

  @Before
  public void setup() {
    t20mins = new Task(20, "");
    t30mins = new Task(30, "");
    t60mins = new Task(60, "");

    calendar = new Calendar();

    LocalDate startDate = LocalDate.ofEpochDay(0);
    ZoneId theZone = ZoneOffset.UTC;
    clock = Clock.fixed(Instant.from(startDate.atStartOfDay(theZone)), theZone);
    localSchedStart = LocalDateTime.now(clock);

    p20mins = new WorkPeriod(localSchedStart, localSchedStart.plusMinutes(20));
    p30mins = new WorkPeriod(localSchedStart.plusMinutes(60), localSchedStart.plusMinutes(90));
  }

  @Test
  public void testNoTasksNoPeriods() {
    Schedule schedule = calendar.createSchedule(clock);
    assertTrue(schedule.getScheduledPeriods().isEmpty());
    assertTrue(schedule.isSuccessful());
  }

  @Test
  public void testAllocateNoWorkPeriods(){
    calendar.addTask(t30mins);
    Schedule schedule = calendar.createSchedule(clock);
    assertFalse(schedule.isSuccessful());
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
  public void testAllocateOneTaskFailure() {
    calendar.addTask(t30mins);
    calendar.addWorkPeriod(p20mins);

    Schedule schedule = calendar.createSchedule(clock);
    assertFalse(schedule.isSuccessful());
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
  public void testAllocateExactFit() {
    /*
    // changing the start time so that the period spans a DST gap makes this test fail, showing that the
		// scheduler doesn't take account of DST changes when calculating the duration of a WorkPeriod
		LocalDate startDate = LocalDate.of(2018, 3, 25);
		ZoneId theZone = ZoneId.of("Europe/London");
		clock = Clock.fixed(Instant.from(startDate.atStartOfDay(theZone)), theZone);
    localSchedStart = LocalDateTime.now(clock);
    */
    calendar.addTask(t60mins);
    p60mins = new WorkPeriod(localSchedStart.plusMinutes(60), localSchedStart.plusMinutes(120));
    Duration p60minsDuration = p60mins.getDuration(clock.getZone());
    calendar.addWorkPeriod(p60mins);

    boolean scheduleSuccess = calendar.createSchedule(clock).isSuccessful();
    boolean periodCanAccommodateTask = p60minsDuration.compareTo(t60mins.getDuration()) == 0;
    assertEquals(scheduleSuccess, periodCanAccommodateTask);
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
