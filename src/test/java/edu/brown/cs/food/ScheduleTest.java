package edu.brown.cs.food;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Test;

import edu.brown.cs.cookups.schedule.LatLong;
import edu.brown.cs.cookups.schedule.Schedule;

public class ScheduleTest {

  @Test
  public void testEquals() {
    LatLong loc1 = new LatLong(-71.435, 41.223);
    LatLong loc2 = new LatLong(-71.4, 40);
    LatLong loc3 = new LatLong(-71.435, 41.223);
    Schedule sched1 = new Schedule(LocalDateTime.of(2015, 4, 24, 4, 56), loc1);
    Schedule sched2 = new Schedule(LocalDateTime.of(2015, 2, 2, 1, 50), loc2); //dif loc and time
    Schedule sched3 = new Schedule(LocalDateTime.of(2015, 2, 2, 1, 15), loc1); //dif time
    Schedule sched4 = new Schedule(LocalDateTime.of(2015, 4, 24, 4, 56), loc2); //dif location
    Schedule sched5 = new Schedule(LocalDateTime.of(2015, 4, 24, 4, 56), loc3);
    assertTrue(sched1.equals(sched1));
    assertFalse(sched1.equals(sched2));
    assertFalse(sched1.equals(sched3));
    assertFalse(sched1.equals(sched4));
    assertTrue(sched1.equals(sched5));
  }
  
  @Test
  public void testEndTime() {
    Schedule sched = new Schedule(LocalDateTime.of(2015, 4, 23, 20, 15), new LatLong(0, 0));
    //set end hour later
    LocalDateTime time = LocalDateTime.of(2015, 4, 23, 21, 15);
    sched.setEnd(time);
    //set end at start
    time = LocalDateTime.of(2015, 4, 23, 20, 15);
    sched.setEnd(time);
    
    //set year before
    boolean caught = false;
    try {
      time = LocalDateTime.of(2014, 4, 23, 20, 15);
      sched.setEnd(time);
    } catch (IllegalArgumentException ie) {
      caught = true;
    }
    assert(caught);
    //set month before
    caught = false;
    try {
      time = LocalDateTime.of(2015, 3, 23, 20, 15);
      sched.setEnd(time);
    } catch (IllegalArgumentException ie) {
      caught = true;
    }
    assert(caught);
    //set day before
    caught = false;
    try {
      time = LocalDateTime.of(2015, 4, 22, 20, 15);
      sched.setEnd(time);
    } catch (IllegalArgumentException ie) {
      caught = true;
    }
    assert(caught);
    //set hour before
    caught = false;
    try {
      time = LocalDateTime.of(2015, 4, 23, 19, 15);
      sched.setEnd(time);
    } catch (IllegalArgumentException ie) {
      caught = true;
    }
    assert(caught);
    //set minute before
    caught = false;
    try {
      time = LocalDateTime.of(2015, 3, 24, 20, 14);
      sched.setEnd(time);
    } catch (IllegalArgumentException ie) {
      caught = true;
    }
    assert(caught);
  }

  @Test
  public void testChangeDate() {
    LocalDate yesterday = LocalDateTime.now().minusDays(1).toLocalDate();
    LocalDate tomorrow = LocalDateTime.now().plusDays(1).toLocalDate();
    Schedule sched = new Schedule(LocalDateTime.now(), null);
    boolean caught = false;
    try {
      sched.changeDate(yesterday);
    } catch (AssertionError e) {
      caught = true;
    }
    assert(caught);
    sched.changeDate(tomorrow);
  }

  @Test
  public void testChangeTime() {
    LocalTime now = LocalDateTime.now().toLocalTime();
    LocalTime minLater = now.plusMinutes(1);
    LocalTime minEarlier = now.minusMinutes(1);
    Schedule sched = new Schedule(LocalDateTime.now(), null);
    boolean caught = false;
    try {
      sched.changeTime(minEarlier);
    } catch (AssertionError e) {
      caught = true;
    }
    assert(caught);
    sched.changeTime(minLater);
  }
}
