package edu.brown.cs.food;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;

import org.junit.Test;

import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Meal;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.User;
import edu.brown.cs.cookups.schedule.LatLong;
import edu.brown.cs.cookups.schedule.Schedule;

public class MealTest {

  @Test
  public void testEquals() {
    Schedule sched = new Schedule(LocalDateTime.now(), new LatLong(10, 10));
    Meal m1 = new Meal(new User("Wes", "wh7", new ArrayList<Ingredient>()), sched);
    Meal m2 = new Meal(new User("Taylor", "tderosa", new ArrayList<Ingredient>()), sched);
    Meal m3 = new Meal(new User("Wes", "wh7", new ArrayList<Ingredient>()), sched);
    m1.addAttending(new User("Grant", "ggustafs", new ArrayList<Ingredient>()));
    m2.addAttending(new User("Albie", "ajb7", new ArrayList<Ingredient>()));
    m3.addAttending(new User("Grant", "ggustafs", new ArrayList<Ingredient>()));
    LocalDate today = LocalDateTime.now().toLocalDate();
    LocalDate tomorrow = LocalDateTime.now().toLocalDate().plusDays(1);
    m1.setDate(today);
    m2.setDate(tomorrow);
    m3.setDate(today);
    Recipe r1 = new Recipe("r1", null);
    Recipe r2 = new Recipe("r2", null);
    m1.addRecipe(r1);
    m2.addRecipe(r2);
    m3.addRecipe(r1);
    LocalTime now = LocalDateTime.now().toLocalTime().plusHours(1);
    LocalTime hourLater = LocalDateTime.now().toLocalTime().plusHours(2);
    m1.setTime(now);
    m2.setTime(hourLater);
    m3.setTime(now);
    assertTrue(m1.equals(m1));
    assertFalse(m1.equals(m2));
    assertTrue(m1.equals(m3));
  }
}
