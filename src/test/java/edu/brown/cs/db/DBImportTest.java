package edu.brown.cs.db;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.SQLException;

import org.junit.Test;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Recipe;

public class DBImportTest {

  public static final String DB_PATH = "databases/tests/importTest.sqlite3";
  public static final String INGREDIENT_PATH = "databases/csv/ingredients/ingredient.csv";
  public static final String RECIPE_PATH = "databases/csv/recipes/gazpacho.csv";

  // @BeforeClass
  public static void setUpClass() throws Exception {

    try {
      DBLink db = new DBLink(DB_PATH);
      db.clearDataBase();
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Test
  public void importIngredients() {
    try {
      DBLink db = new DBLink(DB_PATH);
      db.clearDataBase();
      db.importIngredients(new File(INGREDIENT_PATH));
      assertTrue(db.ingredients()
                   .getIngredientIDByName("Nutmeg")
                   .equals("/i/spice.1"));
      assertTrue(db.ingredients()
                   .getIngredientIDByName("Bacon")
                   .equals("/i/pork.1"));
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void importRecipeTest() {
    try {
      DBLink db = new DBLink(DB_PATH);
      db.clearDataBase();
      db.importIngredients(new File(INGREDIENT_PATH));
      db.importRecipe(new File(RECIPE_PATH));
      Recipe r = db.recipes().getRecipeById("/r/1.6");
      assertTrue(r.name().equals("Gazpacho"));
      assertTrue(r.ingredients()
                  .contains(new Ingredient("/i/liquid.8",
                      4.0,
                      db)));
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
