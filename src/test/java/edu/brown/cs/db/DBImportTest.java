package edu.brown.cs.db;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs.cookups.db.DBLink;

public class DBImportTest {

  public static final String DB_PATH = "databases/tests/importTest.sqlite3";
  public static final String INGREDIENT_PATH = "databases/csv/ingredientTest.csv";
  public static final String RECIPE_PATH = "databases/csv/gazpacho";

  @BeforeClass
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
      db.importIngredients(new File(INGREDIENT_PATH));
      assertTrue(db.ingredients()
                   .getIngredientIDByName("salt")
                   .equals("/i/salt"));
      assertTrue(db.ingredients()
                   .getIngredientIDByName("pepper")
                   .equals("/i/pepper"));
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void importRecipeTest() {
    try {
      DBLink db = new DBLink(DB_PATH);
      db.importIngredients(new File(INGREDIENT_PATH));
      db.importRecipe(new File(""));
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
