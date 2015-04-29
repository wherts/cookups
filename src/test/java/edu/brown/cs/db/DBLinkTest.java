package edu.brown.cs.db;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
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
import edu.brown.cs.cookups.person.PersonManager;
import edu.brown.cs.cookups.person.User;

public class DBLinkTest {

  public static final String TEST_DB = "databases/tests/DBLinkTest.sqlite3";
  public static final String INGREDIENT_PATH = "databases/csv/ingredients/ingredient.csv";
  public static final String RECIPE_PATH = "databases/csv/recipes/gazpacho.csv";
  public static final String RECIPE_DIR = "databases/csv/recipes/";

  @Test
  public void addUserTest() {
    try {
      DBLink db = new DBLink(TEST_DB);
      db.clearDataBase();
      Ingredient i = new Ingredient("i", 1.1, null);
      db.ingredients().defineIngredient("i", "iodine");
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
      db.ingredients().defineIngredient("i", "iodine");
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
      db.ingredients().defineIngredient("a", "apples");
      db.ingredients().defineIngredient("o", "oranges");
      db.ingredients().defineIngredient("c", "carrots");
      db.ingredients().defineIngredient("ch", "chees");
      ingreds.add(new Ingredient("a", 1.0, db));
      ingreds.add(new Ingredient("o", 2.0, db));
      ingreds.add(new Ingredient("c", 3.0, db));
      ingreds.add(new Ingredient("ch", 1.0, db));

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
                                        "Milk");

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
                                        "Milk");

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
                                        "Milk");
      db.ingredients().defineIngredient("/i/dairy.2",
                                        "Cream");
      db.ingredients().defineIngredient("/i/dairy.3",
                                        "Yogurt");
      List<String> names = Arrays.asList("Milk",
                                         "Cream",
                                         "Yogurt");

      List<String> results = db.ingredients()
                               .getAllIngredientNames();
      for (String str : names) {
        assertTrue(results.contains(str));
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
      DBLink db = new DBLink("databases/recipesTest.sqlite3");
      db.ingredients().defineIngredient("/i/salt", "salt");
      db.ingredients().defineIngredient("/i/pepper",
                                        "pepper");
      db.ingredients().defineIngredient("/i/pasta", "");

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
      DBLink db = new DBLink("databases/peopleTest.sqlite3");
      db.ingredients().defineIngredient("/i/freedom",
                                        "freedom");
      db.ingredients().defineIngredient("/i/liberty",
                                        "liberty");
      PersonManager people = new PersonManager(db);
      Person ronald1 = new User("Ronald Reagan",
          "freedom",
          Arrays.asList(new Ingredient("/i/freedom", 42, db)));
      Person ronald2 = new User("Ronald Reagan",
          "liberty",
          Arrays.asList(new Ingredient("/i/liberty", 42, db)));
      people.addPerson("Ronald Reagan",
                       "freedom",
                       Arrays.asList(new Ingredient("/i/freedom",
                           42,
                           db)));
      people.addPerson("Ronald Reagan",
                       "liberty",
                       Arrays.asList(new Ingredient("/i/liberty",
                           42,
                           db)));

      List<Person> results = db.users()
                               .getPersonsByName("Ronald Reagan",
                                                 people);
      assertTrue(results.contains(ronald1));
      assertTrue(results.contains(ronald2));
      db.users().removePersonById("freedom");
      db.users().removePersonById("liberty");

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
      assertTrue(db.recipes()
                   .getInstructionsByRecipe("/r/1.5")
                   .equals(instr));

    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void getIngredientsByRecipe() {
    try {
      DBLink db = new DBLink("databases/cookups.sqlite3");
      List<Ingredient> ingredients = db.ingredients()
                                       .getIngredientsByRecipe("/r/1.2");
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
