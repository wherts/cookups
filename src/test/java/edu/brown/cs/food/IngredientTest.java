package edu.brown.cs.food;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.SQLException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.db.DBManager;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Recipe;

public class IngredientTest {
  private DBManager dbM;
  private static final String DB_PATH = "databases/cookups.sqlite3";

  @Before
  public void initialize() {
    File file = new File("databases/csv/ingredients/ingredientPrice.csv");
    String dir = "databases/csv/recipes";
    try {
      dbM = new DBLink(DB_PATH);
      ((DBLink) dbM).clearDataBase();
      dbM.importIngredients(file);
      dbM.importAllRecipes(dir);
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void basicFunctions() throws SQLException {
    Ingredient soy = new Ingredient("/i/liquid.7", 1, dbM);
    assertTrue(soy.id().equals("/i/liquid.7"));
    assertTrue(soy.name().equals("Soy Sauce"));
    assertTrue(soy.ounces() == 1);
    assertTrue(soy.cups() == 0.125);
    assertTrue(soy.teaspoons() == 6);
    assertTrue(soy.tablespoons() == 2);
  }

  @Test
  public void recipesUsing() throws SQLException {
    Ingredient soy = new Ingredient("/i/liquid.7", 1, dbM);
    Set<Recipe> using = soy.recipes();
    assertTrue(using.size() == 2);
    assertTrue(using.contains(new Recipe("/r/1.9", dbM)));
    assertTrue(using.contains(new Recipe("/r/1.5", dbM)));
  }

  @Test
  public void testStorage() throws SQLException {
    Ingredient soy = new Ingredient("/i/liquid.7", 5, dbM); // soy sauce
    Ingredient pB = new Ingredient("/i/produce.6", 16, dbM); // jar of peanut
                                                             // butter
    assertTrue(soy.storage().equals("Fridge"));
    assertTrue(pB.storage().equals("Pantry"));
  }

  @Test
  public void testPrice() throws SQLException {
    // per ounce prices
    Ingredient soy = new Ingredient("/i/liquid.7", 1, dbM); // soy sauce
    Ingredient pB = new Ingredient("/i/produce.6", 1, dbM); // jar of peanut
                                                            // butter
    assertTrue(soy.price() == 23);
    assertTrue(pB.price() == 30);
    // non unit weights
    soy = new Ingredient("/i/liquid.7", 12, dbM);
    pB = new Ingredient("/i/produce.6", 16, dbM);
    assertTrue(soy.price() == 276);
    assertTrue(pB.price() == 480);
  }
}
