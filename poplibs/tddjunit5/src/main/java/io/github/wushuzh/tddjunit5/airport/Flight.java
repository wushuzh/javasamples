package io.github.wushuzh.tddjunit5.airport;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Flight {
  private String id;
  Set<Passenger> passengerSet = new HashSet<Passenger>();

  public Flight(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public Set<Passenger> getPassengerSet() {
    return Collections.unmodifiableSet(passengerSet);
  }

  public abstract boolean addPassenger(Passenger passenger);

  public abstract boolean removePassenger(Passenger passenger);
}
