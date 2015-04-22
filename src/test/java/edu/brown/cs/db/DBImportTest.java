package edu.brown.cs.db;

import java.io.File;
import java.sql.SQLException;

import org.junit.Test;

import edu.brown.cs.cookups.db.DBLink;

public class DBImportTest {

  public static final String DB_PATH = "databases/importTest.sqlite3";

  // @BeforeClass
  public static void setUpClass() throws Exception {

    try {
      DBLink db = new DBLink(DB_PATH);
      // db.clearDataBase();
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Test
  public void importIngredients() {
    File file = new File("databases/csv/ingredientTest.csv");
    try {
      DBLink db = new DBLink(DB_PATH);
      // db.clearDataBase();
      // db.importIngredients(file);
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
