package io.github.wushuzh.tddjunit5.milage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import io.github.wushuzh.tddjunit5.airport.BusinessFlight;
import io.github.wushuzh.tddjunit5.airport.EconomyFlight;
import io.github.wushuzh.tddjunit5.airport.Flight;
import io.github.wushuzh.tddjunit5.airport.Passenger;
import io.github.wushuzh.tddjunit5.airport.PremiumFlight;

public class FlightArgumentConverter extends SimpleArgumentConverter {

  @Override
  protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
    assertEquals(String.class, source.getClass(), "Can only convert from String");
    assertEquals(Flight.class, targetType, "Can only convert to Flight");

    // 0   1   2      3     4
    // "1; e; Mike; false; 349"
    String[] flightString = source.toString().split(";");
    Flight flight = null;

    switch (flightString[1].toLowerCase().trim()) {
      case "b":
        flight = new BusinessFlight(flightString[0]);
        break;
      case "e":
        flight = new EconomyFlight(flightString[0]);
        break;
      case "p":
        flight = new PremiumFlight(flightString[0]);
        break;
    }

    flight.addPassenger(new Passenger(flightString[2].trim(), Boolean.valueOf(flightString[3].trim())));
    flight.setDistance(Integer.valueOf(flightString[4].trim()));

    return flight;
  }

}
