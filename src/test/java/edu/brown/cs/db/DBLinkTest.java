package edu.brown.cs.db;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.User;

public class DBLinkTest {

  @Test
  public void addUserTest() {
    try {
      DBLink db = new DBLink("databases/testdb.sqlite3");
      Ingredient i = new Ingredient("i", 1.1, null);
      Person p = new User("Jerry", "qyrt", Arrays.asList(i));
      db.addPerson(p);
      assertTrue(db.hasPersonByName("Jerry"));
      db.removePersonById("qyrt");
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  // @Test
  // public void getUserByNameTest() {
  // try {
  // DBLink db = new DBLink("db.sqlite3");
  // Ingredient i = new Ingredient("i", 1.1, null);
  // Person p = new User("Jerry", "qyrt", Arrays.asList(i));
  // db.addPerson(p);
  // Person q = db.getsPersonByName("Jerry");
  // db.removePersonById("qyrt");
  // assertTrue(q.id().equals("qyrt"));
  // assertTrue(q.name().equals("Jerry"));
  // assertTrue(q.ingredients().get(0).id().equals("i"));
  // } catch (ClassNotFoundException | SQLException e) {
  // fail();
  // }
  // }

  @Test
  public void getUserByIDTest() {
    try {
      DBLink db = new DBLink("databases/testdb.sqlite3");
      Ingredient i = new Ingredient("i", 1.1, null);
      Person p = new User("Jerry", "qyrt", Arrays.asList(i));
      db.addPerson(p);
      Person q = db.getPersonById("qyrt");
      db.removePersonById("qyrt");
      assertTrue(q.id().equals("qyrt"));
      assertTrue(q.name().equals("Jerry"));
      assertTrue(q.ingredients().get(0).id().equals("i"));
    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void getRecipesWithIngredient() {
    try {
      DBLink db = new DBLink("databases/cookups.sqlite3");
      // butter
      Set<Recipe> recipes = db.getRecipesWithIngredient("/i/dairy.5");
      assertTrue(recipes.size() == 3);
      List<String> recipeIDs = new ArrayList<String>();
      for (Recipe r : recipes) {
        // System.out.println(r.id());
        recipeIDs.add(r.id());
      }
      assertTrue(recipeIDs.contains("/r/1.2"));
      assertTrue(recipeIDs.contains("/r/1.3"));
      assertTrue(recipeIDs.contains("/r/1.4"));
      // peanut butter
      recipes = db.getRecipesWithIngredient("/i/produce.6");
      assertTrue(recipes.size() == 1);
      for (Recipe r : recipes) {
        assertTrue(r.id().equals("/r/1.5"));
      }
      recipes = db.getRecipesWithIngredient("nonexistent");
      assertTrue(recipes.isEmpty());
    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void getInstructionsByRecipe() {
    try {
      DBLink db = new DBLink("databases/cookups.sqlite3");
      String instr = "1. Cook pasta al dente "
          + "2. Mix all other ingredients. Add pasta while it is still warm. "
          + "Garnish with shredded carrots, cucumber or scallions";
      // System.out.println(db.getInstructionsByRecipe("/r/1.5"));
      assertTrue(db.getInstructionsByRecipe("/r/1.5")
                   .equals(instr));

    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void getIngredientsByRecipe() {
    try {
      DBLink db = new DBLink("databases/cookups.sqlite3");
      List<Ingredient> ingredients = db.getIngredientsByRecipe("/r/1.2");
      Ingredient i1 = new Ingredient("/i/produce.10",
          16.0,
          db);
      Ingredient i2 = new Ingredient("/i/dairy.5", 4.0, db);
      Ingredient i3 = new Ingredient("/i/dairy.2.1",
          8.0,
          db);

      assertTrue(ingredients.size() == 3);
      assertTrue(ingredients.contains(i1));
      assertTrue(ingredients.contains(i2));
      assertTrue(ingredients.contains(i3));
    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

}
