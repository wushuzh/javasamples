package io.github.wushuzh.tddjunit5.airport;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

public class AirportTest {

  @DisplayName("Given there is an Economy flight")
  @Nested
  class EconomyFlightTest {
    private Flight economyFlight;
    private Passenger mike;
    private Passenger john;

    @BeforeEach
    void setUp() {
      economyFlight = new EconomyFlight("1");
      mike = new Passenger("Mike", false);
      john = new Passenger("John", true);
    }

    @Nested
    @DisplayName("When we have an usual passenger")
    class UsualPassenger {

      @Test
      @DisplayName("Then you can add and remove him from an economy flight")
      public void testEconomyFlightUsualPassenger() {
        assertAll("Verify all conditions for an usual passenger and an economy flight",
            () -> assertEquals("1", economyFlight.getId()),
            () -> assertEquals(true, economyFlight.addPassenger(mike)),
            () -> assertEquals(1, economyFlight.getPassengerSet().size()),
            () -> assertTrue(economyFlight.getPassengerSet().contains(mike)),
            () -> assertEquals(true, economyFlight.removePassenger(mike)),
            () -> assertEquals(0, economyFlight.getPassengerSet().size()));
      }

      @DisplayName("Then you cannot add hime to an economy flight more than once")
      @RepeatedTest(5)
      public void testEconomyFlightUsualPassengerAddedOnlyOnce(RepetitionInfo repetionInfo) {
        for (int i = 0; i < repetionInfo.getCurrentRepetition(); i++) {
          economyFlight.addPassenger(mike);
        }
        assertAll("Verify an usual passenger can be added to an economy flight only once",
            () -> assertEquals(1, economyFlight.getPassengerSet().size()),
            () -> assertTrue(economyFlight.getPassengerSet().contains(mike)),
            () -> assertTrue(new ArrayList<>(economyFlight.getPassengerSet())
                .get(0).getName().equals("Mike")));
      }
    }

    @Nested
    @DisplayName("When we have a vip passenger")
    class VipPassenger {
      @Test
      @DisplayName("Then you can add him but cannot remove him from an economy flight")
      public void testEconomyFlightVipPassenger() {
        assertAll("Verify all conditions for a VIP passenger and an economy flight",
            () -> assertEquals("1", economyFlight.getId()),
            () -> assertEquals(true, economyFlight.addPassenger(john)),
            () -> assertEquals(1, economyFlight.getPassengerSet().size()),
            () -> assertTrue(economyFlight.getPassengerSet().contains(john)),
            () -> assertEquals(false, economyFlight.removePassenger(john)),
            () -> assertEquals(1, economyFlight.getPassengerSet().size()));
      }
    }

  }

  @DisplayName("Given there is a business flight")
  @Nested
  class BusinessFlightTest {
    private Flight businessFlight;
    private Passenger mike;
    private Passenger john;

    @BeforeEach
    void setUp() {
      businessFlight = new BusinessFlight("2");
      mike = new Passenger("Mike", false);
      john = new Passenger("John", true);
    }

    @Nested
    @DisplayName("When we have an usual passenger")
    class UsualPassenger {
      @DisplayName("Then you cannot add or remove him from a business flight")
      @Test
      public void testBusinessFlightUsualPassenger() {
        assertAll("Verify all conditions for an usual Passenger and a business flight",
            () -> assertEquals(false, businessFlight.addPassenger(mike)),
            () -> assertEquals(0, businessFlight.getPassengerSet().size()),
            () -> assertEquals(false, businessFlight.removePassenger(mike)),
            () -> assertEquals(0, businessFlight.getPassengerSet().size()));
      }
    }

    @Nested
    @DisplayName("When we have a vip passenger")
    class VipPassenger {
      @DisplayName("Then you can add him but cannot remove him from a business flight")
      @Test
      public void testBusinessFlightVipPassenger() {
        assertAll("Verify all conditions for a vip passenger and a business flight",
            () -> assertEquals(true, businessFlight.addPassenger(john)),
            () -> assertEquals(1, businessFlight.getPassengerSet().size()),
            () -> assertEquals(false, businessFlight.removePassenger(john)),
            () -> assertEquals(1, businessFlight.getPassengerSet().size()));
      }

      @DisplayName("The you cannot add him to a business flight more than once")
      @RepeatedTest(5)
      public void testBusinessFlightVipPassengerOnlyOnce(RepetitionInfo repetitionInfo) {
        for (int i = 0; i < repetitionInfo.getCurrentRepetition(); i++) {
          businessFlight.addPassenger(john);
        }
        assertAll("Verify a VIP passenger can be added to a business flight only once",
            () -> assertEquals(1, businessFlight.getPassengerSet().size()),
            () -> assertTrue(businessFlight.getPassengerSet().contains(john)),
            () -> assertTrue(new ArrayList<>(businessFlight.getPassengerSet())
                .get(0).getName().equals("John")));
      }
    }
  }

  @DisplayName("Given there is a premium flight")
  @Nested
  class PremiumFlightTest {
    private Flight premiumFlight;
    private Passenger mike;
    private Passenger john;

    @BeforeEach
    void setUp() {
      premiumFlight = new PremiumFlight("3");
      mike = new Passenger("Mike", false);
      john = new Passenger("John", true);
    }

    @Nested
    @DisplayName("When we have an usual passenger")
    class UsualPassenger {
      @Test
      @DisplayName("Then you cannot add or remove him from a premium flight")
      public void testPremiumFlightUsualPassenger() {
        assertAll("Verify all conditions for an usual passenger and a premium flight",
            () -> assertEquals("3", premiumFlight.getId()),
            () -> assertEquals(false, premiumFlight.addPassenger(mike)),
            () -> assertEquals(0, premiumFlight.getPassengerSet().size()),
            () -> assertEquals(false, premiumFlight.removePassenger(mike)),
            () -> assertEquals(0, premiumFlight.getPassengerSet().size()));
      }
    }

    @Nested
    @DisplayName("When we have a vip passenger")
    class VipPassenger {
      @Test
      @DisplayName("Then you can add and remove him from a premium flight")
      public void testPremiumFlightVipPassenger() {
        assertAll("Verify all conditions for a vip passenger and a premium flight",
            () -> assertEquals(true, premiumFlight.addPassenger(john)),
            () -> assertEquals(1, premiumFlight.getPassengerSet().size()),
            () -> assertEquals(true, premiumFlight.removePassenger(john)),
            () -> assertEquals(0, premiumFlight.getPassengerSet().size()));
      }

      @DisplayName("Then you cannot add him to a premium flight more than once")
      @RepeatedTest(5)
      public void testPremiumFlightVipPassengerAddOnlyOnce(RepetitionInfo repetitionInfo) {
        for (int i = 0; i < repetitionInfo.getCurrentRepetition(); i++) {
          premiumFlight.addPassenger(john);
        }
        assertAll("Verify a VIP passenger can be added to a premium flight only once",
            () -> assertEquals(1, premiumFlight.getPassengerSet().size()),
            () -> assertTrue(premiumFlight.getPassengerSet().contains(john)),
            () -> assertTrue(new ArrayList<>(premiumFlight.getPassengerSet())
                .get(0).getName().equals("John")));
      }
    }
  }
}
