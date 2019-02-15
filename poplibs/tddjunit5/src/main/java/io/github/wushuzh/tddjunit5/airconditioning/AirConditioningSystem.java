package io.github.wushuzh.tddjunit5.airconditioning;

public class AirConditioningSystem {
  private Thermometer thermometer;
  private double temperatureThreshold;
  private boolean open;

  public AirConditioningSystem() {
    open = false;
  }

  public void setThermometer(Thermometer thermometer) {
    this.thermometer = thermometer;
  }

  public void setTemperatureThreshold(double temparetureThreshold) {
    this.temperatureThreshold = temparetureThreshold;
  }

  public boolean isOpen() {
    return open;
  }

  public void checkAirConditioningSystem() {
    this.open = (thermometer.getTemperature() >= temperatureThreshold);
  }
}
