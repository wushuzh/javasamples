package io.github.wushuzh.core.datetime;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;

public class WorkPeriodTest {
  @Test
  public void basicSplitTest() {
    LocalDateTime startTime = LocalDate.now().atTime(23, 0);
    LocalDateTime endTime = LocalDate.now().plusDays(1).atTime(1, 0);
    WorkPeriod p = new WorkPeriod(startTime, endTime);
    Optional<WorkPeriod> newPeriod = p.split();

    LocalDateTime midnight = LocalDate.now().plusDays(1).atStartOfDay();
    assertEquals(Optional.of(new WorkPeriod(startTime, midnight)), newPeriod);
    assertEquals(new WorkPeriod(midnight, endTime), p);
  }
}
