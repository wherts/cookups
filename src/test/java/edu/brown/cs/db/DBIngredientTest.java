package edu.brown.cs.db;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.SQLException;

import org.junit.Test;

import edu.brown.cs.cookups.db.DBLink;

public class DBIngredientTest {
  public static final String INGREDIENT_PATH = "databases/csv/ingredients/ingredientPrice.csv";
  public static final String DB_PATH = "databases/tests/imgredientTest.sqlite3";

  @Test
  public void priceTest() {
    try {
      DBLink db = new DBLink(DB_PATH);
      db.clearDataBase();
      db.importIngredients(new File(INGREDIENT_PATH));
      assertTrue(100.0 == db.ingredients()
                            .priceByID("/i/spice.1"));
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void storageTest() {
    try {
      DBLink db = new DBLink(DB_PATH);
      db.clearDataBase();
      db.importIngredients(new File(INGREDIENT_PATH));
      assertTrue("Pantry".equals(db.ingredients()
                                   .storageByID("/i/spice.1")));
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
