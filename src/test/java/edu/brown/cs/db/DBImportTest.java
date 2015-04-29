package edu.brown.cs.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import edu.brown.cs.cookups.db.CSVReader;
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
  public void csvTest() {
	  String dir = "databases/csv/recipes";
	  try {
	      DBLink db = new DBLink(DB_PATH);
	      db.importAllRecipes(dir);
	    } catch (ClassNotFoundException | SQLException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
  }
  
 @Test
  public void importIngredientsTest() {
	  File file = new File("databases/csv/ingredients.csv");
	    try {
	      DBLink db = new DBLink(DB_PATH);
	      db.importIngredients(file);
	    } catch (ClassNotFoundException | SQLException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
  }
  
// @Test
 public void hasIngredientTest() {
	  
	    try {
	      DBLink db = new DBLink(DB_PATH);
	      System.out.println(db.ingredients().hasIngredient("/i/produce.2"));
	    } catch (ClassNotFoundException | SQLException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
 }
 
  //@Test
  public void importRecipeTest() {
	  File file = new File("databases/csv/recipes/frittata.csv");
	    try {
	      DBLink db = new DBLink(DB_PATH);
	      db.importRecipe(file);
	    } catch (ClassNotFoundException | SQLException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
  }

  //@Test
  public void importIngredients() {
    File file = new File("databases/csv/ingredientTest.csv");
    try {
      DBLink db = new DBLink(DB_PATH);
      //db.clearDataBase();
      // db.importIngredients(file);
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
