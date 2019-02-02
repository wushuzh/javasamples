package io.github.wushuzh.tddjunit5.airport;

public class Airport {
  public static void main(String[] args) {
    Flight enconomyFlight = new Flight("1", "Economy");
    Flight businessFlight = new Flight("2", "Business");
    
    Passenger john = new Passenger("John", true);
    Passenger mike = new Passenger("Mike", false);
    
    businessFlight.addPassenger(john);
    businessFlight.removePassenger(john);
    businessFlight.addPassenger(mike);
    enconomyFlight.addPassenger(mike);
    
    System.out.println("Business flight passengers list:");
    for (Passenger passenger: businessFlight.getPassengersList()) {
      System.out.println(passenger.getName());
    }
    
    System.out.println("Economy flight passengers list:");
    for (Passenger passenger: enconomyFlight.getPassengersList()) {
      System.out.println(passenger.getName());
    }
  }
}
