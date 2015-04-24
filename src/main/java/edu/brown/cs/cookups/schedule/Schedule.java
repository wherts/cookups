package edu.brown.cs.cookups.schedule;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
/**
 * This class represents a Schedule
 * object for cookups. It includes
 * a date, time, and location
 * @author wh7
 *
 */
public class Schedule {
  private LocalDateTime start, end;
  private LatLong location;

  /**
   * Constructor for a Schedule.
   * @param datetime of event
   * @param location of event
   */
  public Schedule(LocalDateTime datetime, LatLong loc) {
    assert (datetime != null && loc != null);
    this.start = datetime;
    this.location = loc;
  }

  /**
   * Setter for the date.
   * @param lD new date
   * @return old date
   */
  public LocalDate changeDate(LocalDate lD) {
    assert (lD != null);
    LocalDate ret = start.toLocalDate();
    this.start = LocalDateTime.of(lD, start.toLocalTime());
    return ret;
  }

  /**
   * Accessor for date.
   * @return date
   */
  public LocalDate date() {
    return LocalDate.of(start.getYear(),
                        start.getMonthValue(),
                        start.getDayOfMonth());
  }

  /**
   * Setter for the time.
   * @param lT new time
   * @return old time
   */
  public LocalTime changeTime(LocalTime lT) {
    assert (lT != null);
    LocalTime ret = start.toLocalTime();
    this.start = LocalDateTime.of(start.toLocalDate(), lT);
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
  public void setEndTime(LocalDateTime lT) {
    assert (lT != null);
    Period dayDifference = Period.between(start.toLocalDate(), lT.toLocalDate());
    if (dayDifference.getYears() < 0
        || dayDifference.getMonths() < 0
        || dayDifference.getDays() < 0) {
      throw new IllegalArgumentException("ERROR: New end date is before the start date");
    }
    if (dayDifference.getYears() == 0
        && dayDifference.getMonths() == 0
        && dayDifference.getDays() == 0) { //same day, must check time
      Duration timeDifference = Duration.between(start.toLocalTime(), lT.toLocalTime());
      if (timeDifference.isNegative()) {
        throw new IllegalArgumentException("ERROR: New end time is before the start time");
      }
    }
    this.end = lT;
  }

  /**
   * Accessor for the end time.
   * @return null if no end time is
   * set.
   */
  public LocalTime endTime() {
    LocalTime ret = null;
    if (end != null) {
      ret = LocalTime.of(end.getHour(), end.getMinute());
    }
    return ret;
  }

  /**
   * Accessor for end date.
   * @return null if no end date
   * is set
   */
  public LocalDate endDate() {
    LocalDate ret = null;
    if (end != null) {
      ret = LocalDate.of(end.getYear(),
                         end.getMonthValue(),
                         end.getDayOfMonth());
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
    return start.hashCode();
  }

  /**
   * Accessor for string representation
   * of a schedule.
   * @return string
   */
  @Override
  public String toString() {
    return start.toString() + ", " + location.toString();
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
    return this.date().equals(s.date())
           && this.start().equals(s.start())
           && location.equals(s.location());
  }
}
