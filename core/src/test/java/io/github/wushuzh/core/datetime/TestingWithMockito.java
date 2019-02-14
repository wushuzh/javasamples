package io.github.wushuzh.core.datetime;

import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestingWithMockito {
    private Instant currentTime;
    private Clock clock = Mockito.mock(Clock.class);
    private TimePrintingObject testObject;

    @Before
    public void setup(){
        currentTime = Instant.EPOCH; // or other initialisation
        when(clock.instant()).thenAnswer(invocation -> currentTime);
        testObject = new TimePrintingObject();
    }

    @Test
    public void myTest() {
        testObject.methodThatDependsOnTime(clock.instant()); // 1970-01-01T00:00:00Z
        currentTime = currentTime.plus(1, ChronoUnit.DAYS); // simulate passage of time
        testObject.methodThatDependsOnTime(clock.instant()); // 1970-01-02T00:00:00Z
    }
}

class TimePrintingObject {
    void methodThatDependsOnTime(Instant instant) {
        System.out.println(instant);
    }
}