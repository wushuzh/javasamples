package io.github.wushuzh.tddjunit5.airport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AirportTest {

  @Test
  public void testEconomyFlightUsualPassenger() {
    Flight economyFlight = new EconomyFlight("1");
    Passenger mike = new Passenger("Mike", false);

    assertEquals("1", economyFlight.getId());
    assertEquals(true, economyFlight.addPassenger(mike));
    assertEquals(1, economyFlight.getPassengersList().size());
    assertEquals("Mike", economyFlight.getPassengersList().get(0).getName());

    assertEquals(true, economyFlight.removePassenger(mike));
    assertEquals(0, economyFlight.getPassengersList().size());

  }
  
  @Test
  public void testEconomyFlightVipPassenger() {
    Flight economyFlight = new EconomyFlight("1");
    Passenger john = new Passenger("John", true);

    assertEquals("1", economyFlight.getId());
    assertEquals(true, economyFlight.addPassenger(john));
    assertEquals(1, economyFlight.getPassengersList().size());
    assertEquals("John", economyFlight.getPassengersList().get(0).getName());
    
    assertEquals(false, economyFlight.removePassenger(john));
    assertEquals(1, economyFlight.getPassengersList().size());
  }
  
  @Test
  public void testBusinessFlightUsualPassenger() {
    Flight businessFlight = new BusinessFlight("2");
    Passenger mike = new Passenger("Mike", false);
    
    assertEquals(false, businessFlight.addPassenger(mike));
    assertEquals(0,  businessFlight.getPassengersList().size());
    assertEquals(false, businessFlight.removePassenger(mike));
    assertEquals(0, businessFlight.getPassengersList().size());
  }
  
  @Test
  public void testBusinessFlightVipPassenger() {
    Flight businessFlight = new BusinessFlight("2");
    Passenger john = new Passenger("John", true);
    
    assertEquals(true, businessFlight.addPassenger(john));
    assertEquals(1, businessFlight.getPassengersList().size());
    assertEquals(false, businessFlight.removePassenger(john));
    assertEquals(1, businessFlight.getPassengersList().size());
  }
}
