package edu.brown.cs.cookups.schedule;

public class Schedule {
  private double start;
  private double duration;
  private LatLong location;

  public Schedule(double start, double duration, LatLong loc) {
    this.start = start;
    this.duration = duration;
    this.location = loc;
  }

  public double start() {
    return start;
  }

  public double duration() {
    return duration;
  }

  public LatLong location() {
    return location;
  }

  public LatLong setLocation(LatLong loc) {
    LatLong ret = this.location;
    this.location = loc;
    return ret;
  }

  public double setStartTime(double s) {
    double ret = start;
    this.start = s;
    return ret;
  }

  public double setDuration(double d) {
    double ret = duration;
    this.duration = d;
    return ret;
  }
}
