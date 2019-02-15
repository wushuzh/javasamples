package io.github.wushuzh.tddjunit5.airport;

public class PremiumFlight extends Flight {

  public PremiumFlight(String id) {
    super(id);
  }

  @Override
  public boolean addPassenger(Passenger passenger) {
    if (passenger.isVip()) {
      return passengerSet.add(passenger);
    }
    return false;
  }

  @Override
  public boolean removePassenger(Passenger passenger) {
    if (passenger.isVip()) {
      return passengerSet.remove(passenger);
    }
    return false;
  }

}
