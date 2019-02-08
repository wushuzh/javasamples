package io.github.wushuzh.core.datetime;

import static java.util.stream.Collectors.toList;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Utils {

  private static final LocalTime AM_START = LocalTime.of(9, 0);
  private static final LocalTime PM_START = LocalTime.of(13, 30);
  private static final Duration WORK_PERIOD_LENGTH = Duration.ofHours(3).plusMinutes(30);

  public static List<WorkPeriod> generateWorkPeriods(LocalDate date, int dayCountInclusive) {
    List<LocalDate> workingDays = generateWorkingDays(date, dayCountInclusive);
    return generateWorkPeriods(
        workingDays, AM_START, WORK_PERIOD_LENGTH, PM_START, WORK_PERIOD_LENGTH);
  }

  private static List<WorkPeriod> generateWorkPeriods(
      List<LocalDate> days,
      LocalTime amStart, Duration amDuration,
      LocalTime pmStart, Duration pmDuration) {
    List<WorkPeriod> periods = new ArrayList<>();
    for (LocalDate d : days) {
      LocalDateTime thisAmStart = LocalDateTime.of(d, amStart);
      periods.add(new WorkPeriod(thisAmStart, thisAmStart.plus(amDuration)));
      LocalDateTime thisPmStart = LocalDateTime.of(d, pmStart);
      periods.add(new WorkPeriod(thisPmStart, thisPmStart.plus(pmDuration)));
    }
    return periods;
  }

  private static List<LocalDate> generateWorkingDays(LocalDate startDate, int dayCount) {
    return Stream.iterate(startDate, d -> d.plusDays(1))
        .filter(Utils::isWorkingDay)
        .limit(dayCount)
        .collect(toList());
  }

  public static boolean isWorkingDay(LocalDate d) {
    return d.getDayOfWeek() != DayOfWeek.SATURDAY && d.getDayOfWeek() != DayOfWeek.SUNDAY;
  }
}
