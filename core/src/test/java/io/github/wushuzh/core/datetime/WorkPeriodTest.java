package io.github.wushuzh.core.datetime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WorkPeriodTest {
  private LocalDate startLocalDate;

  @Before
  public void setup() {
    startLocalDate = LocalDate.ofEpochDay(0);
  }

  @Test
  public void basicSplitTest() {
    LocalDateTime startTime = startLocalDate.atTime(23, 0);
    LocalDateTime endTime = startLocalDate.plusDays(1).atTime(1, 0);
    WorkPeriod p = new WorkPeriod(startTime, endTime);
    Optional<WorkPeriod> newPeriod = p.split();

    LocalDateTime midnight = startLocalDate.plusDays(1).atStartOfDay();
    assertEquals(Optional.of(new WorkPeriod(startTime, midnight)), newPeriod);
    assertEquals(new WorkPeriod(midnight, endTime), p);
  }

  @Test
  public void testSplitTwoDaysPeriod() {
    LocalDateTime startTime = startLocalDate.atTime(0, 0);
    LocalDateTime endTime = startLocalDate.plusDays(2).atTime(0, 0);
    WorkPeriod p = new WorkPeriod(startTime, endTime);
    Optional<WorkPeriod> splitWorkPeriod = p.split();

    LocalDateTime midnight = startLocalDate.plusDays(1).atStartOfDay();
    assertEquals(Optional.of(new WorkPeriod(startTime, midnight)), splitWorkPeriod);
    assertEquals(new WorkPeriod(midnight, endTime), p);
  }

  @Test
  public void testSplitOnStartTime() {
    LocalDateTime startLDT = startLocalDate.atTime(22, 0);
    WorkPeriod p = new WorkPeriod(startLDT, startLocalDate.atTime(23, 0));
    Optional<WorkPeriod> splitNull = p.split(startLDT);
    assertFalse(splitNull.isPresent());
  }

  @Test
  public void testSplitOnEndTime() {
    WorkPeriod p = new WorkPeriod(startLocalDate.atTime(22, 0), startLocalDate.atTime(23, 0));
    p.split(startLocalDate.atTime(23, 0));
    assertTrue(p.getStartTime().equals(p.getEndTime()));
  }

  @Test
  public void testSplitWithSingleTaskPart() {
    LocalDateTime startTime = startLocalDate.atTime(23, 0);
    LocalDateTime endTime = startLocalDate.plusDays(1).atTime(1, 0);
    WorkPeriod p = new WorkPeriod(startTime, endTime);
    Task t = new Task(0, 120, "");
    p.addTaskPart(TaskPart.wholeOf(t));
    Optional<WorkPeriod> newFirstHalf = p.split();

    LocalDateTime midnight = startLocalDate.plusDays(1).atStartOfDay();
    WorkPeriod expectedFirstHalf = new WorkPeriod(startTime, midnight);
    expectedFirstHalf.addTaskPart(new TaskPart(t, Duration.ofMinutes(60), 1));
    assertEquals(Optional.of(expectedFirstHalf), newFirstHalf);

    WorkPeriod expectedSecondHalf = new WorkPeriod(midnight, endTime);
    expectedSecondHalf.addTaskPart(new TaskPart(t, Duration.ofMinutes(60), 2));
    assertEquals(expectedSecondHalf, p);
  }

  @Test
  public void testSplitWithExactTwoTaskPartsBoundary() {
    LocalDateTime startTime = startLocalDate.atTime(23, 0);
    LocalDateTime endTime = startLocalDate.plusDays(1).atTime(1, 0);
    WorkPeriod p = new WorkPeriod(startTime, endTime);
    Task t1 = new Task(0, 60, "");
    TaskPart tp1 = TaskPart.wholeOf(t1);
    Task t2 = new Task(0, 60, "");
    TaskPart tp2 = TaskPart.wholeOf(t2);
    p.addTaskPart(tp1);
    p.addTaskPart(tp2);
    Optional<WorkPeriod> newFirstHalf = p.split();

    LocalDateTime midnight = startLocalDate.plusDays(1).atStartOfDay();
    WorkPeriod expectedFirstHalf = new WorkPeriod(startTime, midnight);
    expectedFirstHalf.addTaskPart(tp1);
    assertEquals(Optional.of(expectedFirstHalf), newFirstHalf);
    WorkPeriod expectedSecondHalf = new WorkPeriod(midnight, endTime);
    expectedSecondHalf.addTaskPart(tp2);
    assertEquals(expectedSecondHalf, p);
  }

  @Test
  public void testSplitWithNotExactTaskPartsBoundaries() {
    LocalDateTime startTime = startLocalDate.atTime(23, 0);
    LocalDateTime endTime = startLocalDate.plusDays(1).atTime(1, 0);
    WorkPeriod p = new WorkPeriod(startTime, endTime);
    Task t1 = new Task(0, 40, "");
    TaskPart tp1 = TaskPart.wholeOf(t1);
    Task t2 = new Task(0, 40, "");
    TaskPart tp2 = TaskPart.wholeOf(t2);
    Task t3 = new Task(0, 20, "");
    TaskPart tp3 = TaskPart.wholeOf(t3);
    p.addTaskPart(tp1);
    p.addTaskPart(tp2);
    p.addTaskPart(tp3);
    Optional<WorkPeriod> newFirstHalf = p.split();

    LocalDateTime midnight = endTime.withHour(0);
    WorkPeriod expectedFirstHalf = new WorkPeriod(startTime, midnight);
    expectedFirstHalf.addTaskPart(tp1);
    expectedFirstHalf.addTaskPart(new TaskPart(t2, Duration.ofMinutes(20), 1));
    assertEquals(Optional.of(expectedFirstHalf), newFirstHalf);

    WorkPeriod expectedSecondHalf = new WorkPeriod(midnight, endTime);
    expectedSecondHalf.addTaskPart(new TaskPart(t2, Duration.ofMinutes(20), 1));
    expectedSecondHalf.addTaskPart(tp3);
    assertEquals(expectedSecondHalf, p);
  }

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void testOverLongPeriodRejected() {
    LocalDateTime startTime = startLocalDate.atTime(0, 1);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Periods cannot span more than two days");
    new WorkPeriod(startTime, startTime.plusDays(2));
  }

  @Test
  public void startMidnightMoreThan24Hours() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("cannot span more than two days");
    new WorkPeriod(startLocalDate.atTime(0, 0), startLocalDate.plusDays(2).atTime(0, 1));
  }
}
