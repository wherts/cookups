package edu.brown.cs.cookups;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class RecipeMatchTest {

  @Test
  public void recipeCompilation() {
    RecipeMatcher rM = new RecipeMatcher();
    List<Person> chefs = new ArrayList<>();
    DBLink dbL = null;
    try {
      dbL = new DBLink("db.sqlite3");
    } catch (ClassNotFoundException | SQLException e) {
      System.err.println("TESTING ERROR");
      fail();
    }
    double[] weights = {5.0, 9.0, 80.0, 100.0, 999.0};
    String[] ids = {"one", "two", "three", "four", "five"};
    List<Ingredient> ings1 = new ArrayList<>();
    List<Ingredient> ings2 = new ArrayList<>();
    List<Ingredient> ings3 = new ArrayList<>();
    List<Ingredient> ings4 = new ArrayList<>();
    List<Person> people = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      ings1.add(new Ingredient(ids[i], weights[i], dbL));
      ings2.add(new Ingredient(ids[i], weights[i] * 10, dbL));
      ings3.add(new Ingredient(ids[i], weights[i] * 100, dbL));
      ings4.add(new Ingredient(ids[i], weights[i] * 1000, dbL));
    }
    people.add(new User("Albie", "ajb7", ings1));
    people.add(new User("Wes", "wh7", ings2));
    people.add(new User("Taylor", "tderosa", ings3));
    people.add(new User("Grant", "ggustafs", ings4));
    Map<String, Double> map = new HashMap<>();
    rM.compileIngredients(people, map);
    assertTrue(map.get("one") == 5555.0);
    assertTrue(map.get("two") == 9999.0);
    assertTrue(map.get("three") == 88880.0);
    assertTrue(map.get("four") == 111100.0);
    assertTrue(map.get("five") == 1109889.0);
  }

  @Test
  public void testIngredientMatching() {
    
  }
}