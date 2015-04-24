package edu.brown.cs.cookups.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
/**
 * This class represents a Schedule
 * object for cookups. It includes
 * a date, time, and location
 * @author wh7
 *
 */
public class Schedule {
  private LocalDate date;
  private LocalTime start, end;
  private LatLong location;

  /**
   * Constructor for a Schedule.
   * @param datetime of event
   * @param location of event
   */
  public Schedule(LocalDateTime datetime, LatLong loc) {
    this.date = datetime.toLocalDate();
    this.start = datetime.toLocalTime();
    this.location = loc;
  }

  /**
   * Setter for the date.
   * @param lD new date
   * @return old date
   */
  public LocalDate changeDate(LocalDate lD) {
    assert (lD != null);
    LocalDate ret = date;
    this.date = lD;
    return ret;
  }

  /**
   * Accessor for date.
   * @return date
   */
  public LocalDate date() {
    return LocalDate.of(date.getYear(),
                        date.getMonthValue(),
                        date.getDayOfMonth());
  }

  /**
   * Setter for the time.
   * @param lT new time
   * @return old time
   */
  public LocalTime changeTime(LocalTime lT) {
    assert (lT != null);
    LocalTime ret = start;
    this.start = lT;
    return ret;
  }

  /**
   * Accessor for start time.
   * @return time object
   */
  public LocalTime start() {
    return LocalTime.of(start.getHour(), start.getMinute());
  }

  /**
   * Setter for an end time.
   * @param lT end time
   */
  public void setEndTime(LocalTime lT) {
    assert (lT != null);
    this.end = lT;
  }

  /**
   * Accessor for the end time.
   * @return null if no end time is
   * set.
   */
  public LocalTime end() {
    LocalTime ret = null;
    if (end != null) {
      ret = LocalTime.of(end.getHour(), end.getMinute());
    }
    return ret;
  }

  /**
   * Setter for location.
   * @param lL new location
   * @return old location
   */
  public LatLong changeLocation(LatLong lL) {
    assert (lL != null);
    LatLong ret = location;
    this.location = lL;
    return ret;
  }

  /**
   * Accessor for location.
   * @return location
   */
  public LatLong location() {
    return new LatLong(location.getLat(), location.getLong());
  }

  /**
   * Accessor for hashing schedule.
   * @return int for hashing
   */
  @Override
  public int hashCode() {
    return date.hashCode() + start.hashCode();
  }

  /**
   * Accessor for string representation
   * of a schedule.
   * @return string
   */
  @Override
  public String toString() {
    return date.toString() + ", "
           + start.toString();
  }

  /**
   * Accessor for Schedule equality.
   * @return true if object is equal
   * to the schedule's date, time,
   * and location.
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Schedule)) {
      return false;
    }
    Schedule s = (Schedule) o;
    return date.equals(s.date())
           && start.equals(s.start())
           && location.equals(s.location());
  }
}
