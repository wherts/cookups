package edu.brown.cs.db;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;
import edu.brown.cs.cookups.person.User;

public class DBLinkTest {

  public static final String TEST_DB = "databases/tests/DBLinkTest.sqlite3";
  public static final String INGREDIENT_PATH = "databases/csv/ingredients/ingredientExpiration.csv";
  public static final String RECIPE_PATH = "databases/csv/recipes/gazpacho.csv";
  public static final String RECIPE_DIR = "databases/csv/recipes/";

  @Test
  public void addUserTest() {
    try {
      DBLink db = new DBLink(TEST_DB);
      db.clearDataBase();
      Ingredient i = new Ingredient("i", 1.1, null);
      i.setDateCreated(LocalDateTime.now());
      db.ingredients().defineIngredient("i",
                                        "iodine",
                                        1.0,
                                        "Pantry",
                                        1);
      Person p = new User("Jerry", "qyrt", Arrays.asList(i));
      db.users().addPerson(p);
      assertTrue(db.users().hasPersonByName("Jerry"));
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void getUserByIDTest() {
    try {
      DBLink db = new DBLink(TEST_DB);
      Ingredient i = new Ingredient("i", 1.1, null);
      i.setDateCreated(LocalDateTime.now());
      db.ingredients().defineIngredient("i",
                                        "iodine",
                                        1.0,
                                        "Pantry",
                                        1);
      Person p = new User("Jerry", "qyrt", Arrays.asList(i));
      db.users().addPerson(p);
      Person q = db.users().getPersonById("qyrt");
      assertTrue(q.id().equals("qyrt"));
      assertTrue(q.name().equals("Jerry"));
      assertTrue(q.ingredients().get(0).id().equals("i"));
    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void getUserIngredientstest() {
    try {
      DBLink db = new DBLink(TEST_DB);
      db.clearDataBase();
      PersonManager people = new PersonManager(db);
      List<Ingredient> ingreds = new ArrayList<Ingredient>();
      db.ingredients().defineIngredient("a",
                                        "apples",
                                        1.0,
                                        "Pantry",
                                        1);
      db.ingredients().defineIngredient("o",
                                        "oranges",
                                        1.0,
                                        "Pantry",
                                        1);
      db.ingredients().defineIngredient("c",
                                        "carrots",
                                        1.0,
                                        "Pantry",
                                        1);
      db.ingredients().defineIngredient("ch",
                                        "chees",
                                        1.0,
                                        "Pantry",
                                        1);
      ingreds.add(new Ingredient("a", 1.0, db));
      ingreds.add(new Ingredient("o", 2.0, db));
      ingreds.add(new Ingredient("c", 3.0, db));
      ingreds.add(new Ingredient("ch", 1.0, db));
      for (Ingredient i : ingreds) {
        i.setDateCreated(LocalDateTime.now());
      }

      people.addPerson("Ronald Reagan", "freedom", ingreds);

      for (Ingredient i : db.users()
                            .getPersonIngredients("freedom")) {
        assertTrue(ingreds.contains(i));
      }

      db.users().removePersonById("freedom");

    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void getIngredientIDByName() {
    try {
      DBLink db = new DBLink(TEST_DB);
      db.clearDataBase();
      db.ingredients().defineIngredient("/i/dairy.1",
                                        "Milk",
                                        1.0,
                                        "Fridge",
                                        1);

      assertTrue(db.ingredients()
                   .getIngredientIDByName("Milk")
                   .equals("/i/dairy.1"));

    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void getIngredientNamebyID() {
    try {
      DBLink db = new DBLink(TEST_DB);
      db.clearDataBase();
      db.ingredients().defineIngredient("/i/dairy.1",
                                        "Milk",
                                        1.0,
                                        "Fridge",
                                        1);

      assertTrue(db.ingredients()
                   .getIngredientNameByID("/i/dairy.1")
                   .equals("Milk"));

    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void getRecipeName() {
    try {
      DBLink db = new DBLink(TEST_DB);
      db.clearDataBase();
      db.importIngredients(new File(INGREDIENT_PATH));
      db.importRecipe(new File(RECIPE_PATH));
      Recipe r = db.recipes().getRecipeById("/r/1.6");
      assertTrue("Gazpacho".equals(r.name()));
    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void getIngredientNames() {
    try {
      DBLink db = new DBLink(TEST_DB);
      db.ingredients().defineIngredient("/i/dairy.1",
                                        "Milk",
                                        1.0,
                                        "Fridge",
                                        1);
      db.ingredients().defineIngredient("/i/dairy.2",
                                        "Cream",
                                        1.0,
                                        "Fridge",
                                        1);
      db.ingredients().defineIngredient("/i/dairy.3",
                                        "Yogurt",
                                        1.0,
                                        "Fridge",
                                        1);
      List<String> names = Arrays.asList("Milk",
                                         "Cream",
                                         "Yogurt");

      List<String> fridge = db.ingredients()
                               .getAllIngredientNames("Fridge");
      for (String str : names) {
        assertTrue(fridge.contains(str));
      }

    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void getRecipesWithIngredient() {
    try {
      DBLink db = new DBLink(TEST_DB);
      db.clearDataBase();
      String dir = "databases/csv/recipes/";
      String r1 = dir + "mashedPotatoes.csv";
      String r2 = dir + "frenchOnionSoup.csv";
      String r3 = dir + "frittata.csv";
      db.importIngredients(new File(INGREDIENT_PATH));
      db.importRecipe(new File(r1));
      db.importRecipe(new File(r2));
      db.importRecipe(new File(r3));
      Set<Recipe> recipes = db.recipes()
                              .getRecipesWithIngredient("/i/dairy.5");

      List<String> recipeIDs = new ArrayList<String>();
      for (Recipe r : recipes) {
        // System.out.println(r.id());
        recipeIDs.add(r.id());
      }
      assertTrue(recipeIDs.contains("/r/1.2"));
      assertTrue(recipeIDs.contains("/r/1.3"));
      assertTrue(recipeIDs.contains("/r/1.4"));
      recipes = db.recipes()
                  .getRecipesWithIngredient("nonexistent");
      assertTrue(recipes.isEmpty());
    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void addRecipeTest() {
    try {
      DBLink db = new DBLink(TEST_DB);
      db.clearDataBase();
      // butter
      db.recipes().addRecipe("Beef Stew",
                             "/r/stew",
                             "Put in Bowl and Shake");
      assertTrue(db.recipes().hasRecipe("/r/stew"));
      db.recipes().removeRecipe("/r/stew");
    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void recipeAddIngredientsTest() {
    try {
      DBLink db = new DBLink(TEST_DB);
      db.clearDataBase();
      db.ingredients().defineIngredient("/i/salt",
                                        "salt",
                                        1.0,
                                        "Pantry",
                                        1);
      db.ingredients().defineIngredient("/i/pepper",
                                        "pepper",
                                        1.0,
                                        "Pantry",
                                        1);
      db.ingredients().defineIngredient("/i/pasta",
                                        "pasta",
                                        1.0,
                                        "Pantry",
                                        1);

      db.recipes().addRecipe("Beef Stew",
                             "/r/stew",
                             "Put in Bowl and Shake");
      db.recipes().addRecipeIngredient("/r/stew",
                                       "/i/salt",
                                       1);
      db.recipes().addRecipeIngredient("/r/stew",
                                       "/i/pepper",
                                       4);
      db.recipes().addRecipeIngredient("/r/stew",
                                       "/i/pasta",
                                       7);

      List<Ingredient> results = db.ingredients()
                                   .getIngredientsByRecipe("/r/stew");
      assertTrue(results.contains(new Ingredient("/i/salt",
          1,
          db)));
      assertTrue(results.contains(new Ingredient("/i/pepper",
          4,
          db)));
      assertTrue(results.contains(new Ingredient("/i/pasta",
          7,
          db)));
      assertTrue(db.recipes().hasRecipe("/r/stew"));
      db.recipes().removeRecipe("/r/stew");
    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void getPersonsByName() {
    try {
      DBLink db = new DBLink(TEST_DB);
      db.clearDataBase();
      db.ingredients().defineIngredient("/i/freedom",
                                        "freedom",
                                        1776.0,
                                        "USA",
                                        1);
      db.ingredients().defineIngredient("/i/liberty",
                                        "liberty",
                                        1776.0,
                                        "USA",
                                        1);
      PersonManager people = new PersonManager(db);
      Ingredient freedom = new Ingredient("/i/freedom",
          42,
          db);
      freedom.setDateCreated(LocalDateTime.now());
      Person ronald1 = people.addPerson("Ronald Reagan",
                                        "freedom",
                                        Arrays.asList(freedom));
      Ingredient liberty = new Ingredient("/i/liberty",
          42,
          db);
      liberty.setDateCreated(LocalDateTime.now());
      Person ronald2 = people.addPerson("Ronald Reagan",
                                        "liberty",
                                        Arrays.asList(liberty));

      List<Person> results = db.users()
                               .getPersonsByName("Ronald Reagan",
                                                 people);
      assertTrue(results.contains(ronald1));
      assertTrue(results.contains(ronald2));

    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  // @Test
  public void getInstructionsByRecipe() {
    try {
      DBLink db = new DBLink(TEST_DB);

      String instr = "1. Cook pasta al dente "
          + "2. Mix all other ingredients. Add pasta while it is still warm. "
          + "Garnish with shredded carrots, cucumber or scallions";
      // System.out.println(db.getInstructionsByRecipe("/r/1.5"));
      assertTrue(db.recipes()
                   .getInstructionsByRecipe("/r/1.5")
                   .equals(instr));

    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  // @Test
  public void getIngredientsByRecipe() {
    try {
      DBLink db = new DBLink(TEST_DB);
      db.clearDataBase();
      db.importIngredients(new File(INGREDIENT_PATH));
      db.importRecipe(new File(RECIPE_PATH));
      List<Ingredient> ingredients = db.ingredients()
                                       .getIngredientsByRecipe("/r/1.2");
      Ingredient i1 = new Ingredient("/i/produce.10",
          16.0,
          db);
      Ingredient i2 = new Ingredient("/i/dairy.5", 4.0, db);
      Ingredient i3 = new Ingredient("/i/dairy.2.1",
          8.0,
          db);
      for (Ingredient i : ingredients) {
        System.out.println(i.id() + ", " + i.ounces());
      }
      // // assertTrue(ingredients.size() == 3);
      // assertTrue(ingredients.contains(i1));
      // assertTrue(ingredients.contains(i2));
      // assertTrue(ingredients.contains(i3));
    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

}
