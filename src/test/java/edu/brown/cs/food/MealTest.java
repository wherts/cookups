package edu.brown.cs.food;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import org.junit.Test;

import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Meal;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.User;

public class MealTest {

  @Test
  public void testEquals() {
    Meal m1 = new Meal(new User("Wes", "wh7", new ArrayList<Ingredient>()));
    Meal m2 = new Meal(new User("Taylor", "tderosa", new ArrayList<Ingredient>()));
    Meal m3 = new Meal(new User("Wes", "wh7", new ArrayList<Ingredient>()));
    m1.addAttending(new User("Grant", "ggustafs", new ArrayList<Ingredient>()));
    m2.addAttending(new User("Albie", "ajb7", new ArrayList<Ingredient>()));
    m3.addAttending(new User("Grant", "ggustafs", new ArrayList<Ingredient>()));
    LocalDate d1 = LocalDate.of(2015, Month.APRIL, 22);
    LocalDate d2 = LocalDate.of(2015, Month.APRIL, 12);
    m1.setDate(d1);
    m2.setDate(d2);
    m3.setDate(d1);
    Recipe r1 = new Recipe("r1", null);
    Recipe r2 = new Recipe("r2", null);
    m1.changeRecipe(r1);
    m2.changeRecipe(r2);
    m3.changeRecipe(r1);
    assertTrue(m1.equals(m1));
    assertFalse(m1.equals(m2));
    assertTrue(m1.equals(m3));
  }
}