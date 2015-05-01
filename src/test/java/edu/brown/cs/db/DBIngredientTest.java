package edu.brown.cs.db;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs.cookups.db.DBLink;

public class DBIngredientTest {
  public static final String INGREDIENT_PATH = "databases/csv/ingredients/ingredientExpiration.csv";
  public static final String DB_PATH = "databases/tests/imgredientTest.sqlite3";

  private static DBLink db;

  @BeforeClass
  public static void setup() {
    try {
      db = new DBLink(DB_PATH);
      db.clearDataBase();
      db.importIngredients(new File(INGREDIENT_PATH));
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void priceTest() {
    try {
      db = new DBLink(DB_PATH);
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
      db = new DBLink(DB_PATH);

      assertTrue("Pantry".equals(db.ingredients()
                                   .storageByID("/i/spice.1")));
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void expirationTest() {
    try {
      db = new DBLink(DB_PATH);

      assertTrue(4320 == db.ingredients()
                           .expirationByID("/i/citrus.1"));
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
