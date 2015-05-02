package edu.brown.cs.db;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Meal;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;
import edu.brown.cs.cookups.person.User;
import edu.brown.cs.cookups.schedule.LatLong;
import edu.brown.cs.cookups.schedule.Schedule;

public class DBMealTest {
  private static Gson gson = new Gson();
  public static final String DB_PATH = "databases/tests/mealTest.sqlite3";

  private static DBLink db = null;

  @BeforeClass
  public static void setup() {
    try {
      db = new DBLink(DB_PATH);
      db.clearDataBase();
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void mealJSON() {
	 if(db.meals() == null) {
		 System.out.println("FUCK");
	 }
	PersonManager people = new PersonManager(db);
    Schedule sched = new Schedule(LocalDateTime.now(),
        new LatLong(10, 10));
    Person g = people.addPerson("Grant", "wh7", new ArrayList<Ingredient>());
    Person w = people.addPerson("Wes",
        "wh7",
        new ArrayList<Ingredient>());
    Meal m1 = new Meal(w, sched);
    m1.addAttending(g);
    Recipe r1 = new Recipe("r1", db);
    m1.addRecipe(r1);
    String id = db.meals().addMeal(m1);
    Meal m2 = db.meals().getMealByID(id, people);
  }
  
}
