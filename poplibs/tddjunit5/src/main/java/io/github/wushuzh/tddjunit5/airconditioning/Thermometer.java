package io.github.wushuzh.tddjunit5.airconditioning;

/**
 * Thermometer
 */
public class Thermometer {

  private double temperature;
  private Sensor sensor;

  public Sensor getSensor() {
    return sensor;
  }

  public double getTemperature() {
    if (sensor.isBlocked()) {
      throw new RuntimeException("Sensor is blocked");
    }
    return temperature;
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  public void setSensor(Sensor sensor) {
    this.sensor = sensor;
  }

}
