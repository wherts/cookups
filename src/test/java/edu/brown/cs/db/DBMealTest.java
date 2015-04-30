package edu.brown.cs.db;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

import com.google.gson.Gson;

import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Meal;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.User;
import edu.brown.cs.cookups.schedule.LatLong;
import edu.brown.cs.cookups.schedule.Schedule;

public class DBMealTest {
  private static Gson gson = new Gson();

  @Test
  public void mealJSON() {
    Schedule sched = new Schedule(LocalDateTime.now(),
        new LatLong(10, 10));
    Meal m1 = new Meal(new User("Wes",
        "wh7",
        new ArrayList<Ingredient>()), sched);
    m1.addAttending(new User("Grant",
        "ggustafs",
        new ArrayList<Ingredient>()));
    Recipe r1 = new Recipe("r1", null);
    m1.addRecipe(r1);
    String json = gson.toJson(m1);
    System.out.println(json);
    Meal m2 = gson.fromJson(json, Meal.class);

  }
}
