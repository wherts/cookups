package edu.brown.cs.food;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.cookups.RecipeMatcher;
import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.db.DBManager;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.User;

public class RecipeTest {
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
  public void testEquals() {
    Recipe r1 = new Recipe("/r/1.1", null);
    Recipe r2 = new Recipe("/r/1.2", null);
    Recipe r3 = new Recipe("/r/1.1", null);
    assertTrue(r1.equals(r1));
    assertFalse(r1.equals(r2));
    assertTrue(r1.equals(r3));
  }

  @Test
  public void testPercentage() {
    Recipe rec = new Recipe("test recipe", null);
    List<Ingredient> toBuy = new ArrayList<>();
    List<Ingredient> ingredients = new ArrayList<>();
    // simple numbers
    double[] totals = {15.0, 8.0, 7.0, 2.0, 3.0};
    double[] shop = {10.0, 4.0, 4.0, 2.0, 1.0};
    String[] ids = {"eggs", "butter", "cheese", "onions", "bacon"};
    for (int i = 0; i < 5; i++) {
      ingredients.add(new Ingredient(ids[i], totals[i], null));
      rec.addToShoppingList(new Ingredient(ids[i], 0, null), shop[i]);
    }
    rec.setIngredients(ingredients);
    assertTrue(rec.percentHave() == 0.6);
    // more complex numbers
    double[] t = {15.0, 12.6, 7.5, 0.55, 3.27};
    double[] h = {4.4, 12, 4.8, 0.17, 3.2};
    rec = new Recipe("more complex test", null);
    ingredients = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      ingredients.add(new Ingredient(ids[i], t[i], null));
      rec.addToShoppingList(new Ingredient(ids[i], 0, null), h[i]);
    }
    rec.setIngredients(ingredients);
    double percent = rec.percentHave();
    double rounded = Math.floor(percent * 1000) / 1000;
    assertTrue(rounded == 0.631);
  }

  @Test
  public void testPriceOnePerson() throws SQLException {
    // test half of every item
    double[] half = {0.25, 0.5, 2, 0.5, 0.5, 2, 2, 2};
    String[] ids =
        {"/i/liquid.10", "/i/liquid.3.2", "/i/liquid.8", "/i/liquid.9",
            "/i/produce.1.3",
            "/i/produce.10", "/i/produce.8", "/i/produce.9"};
    List<Ingredient> ings = new ArrayList<>();
    for (int i = 0; i < 8; i++) {
      ings.add(new Ingredient(ids[i], half[i], dbM));
    }
    List<Person> chef = new ArrayList<>();
    chef.add(new User("Wes", "wh7", ings));
    List<Recipe> recipes = RecipeMatcher.matchRecipes(chef, dbM);
    assertTrue(recipes.size() == 9);
    // just looking at one recipe here
    Recipe gaz = recipes.get(7);
    assertTrue(gaz.name().equals("Gazpacho"));
    assertTrue(gaz.id().equals("/r/1.6"));
    assertTrue(gaz.shoppingPrice() == 1.64);
    // half the items
    chef = new ArrayList<>();
    ings = new ArrayList<>();
    double[] amt = {0.5, 1, 4, 1};
    for (int i = 0; i < 4; i++) {
      ings.add(new Ingredient(ids[i], amt[i], dbM));
    }
    chef.add(new User("Wes", "wh7", ings));
    recipes = RecipeMatcher.matchRecipes(chef, dbM);
    assertTrue(recipes.size() == 9);
    gaz = recipes.get(7);
    assertTrue(gaz.name().equals("Gazpacho"));
    assertTrue(gaz.id().equals("/r/1.6"));
    assertTrue(gaz.shoppingPrice() == 1.87);
    // test all of every item
    chef = new ArrayList<>();
    ings = new ArrayList<>();
    double[] all = {0.5, 1, 4, 1, 1, 4, 4, 4};
    for (int i = 0; i < 8; i++) {
      ings.add(new Ingredient(ids[i], all[i], dbM));
    }
    chef.add(new User("Wes", "wh7", ings));
    recipes = RecipeMatcher.matchRecipes(chef, dbM);
    assertTrue(recipes.size() == 9);
    gaz = recipes.get(7);
    assertTrue(gaz.name().equals("Gazpacho"));
    assertTrue(gaz.id().equals("/r/1.6"));
    assertTrue(gaz.shoppingPrice() == 0);
  }

  @Test
  public void testPriceTwoPeople() throws SQLException {
    // half of each item
    double[] half = {0.5, 1, 4, 1, 1, 4, 4, 4};
    String[] ids =
        {"/i/liquid.10", "/i/liquid.3.2", "/i/liquid.8", "/i/liquid.9",
            "/i/produce.1.3",
            "/i/produce.10", "/i/produce.8", "/i/produce.9"};
    List<Ingredient> ings1 = new ArrayList<>();
    List<Ingredient> ings2 = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      ings1.add(new Ingredient(ids[i], half[i], dbM));
      ings2.add(new Ingredient(ids[i + 4], half[i + 4], dbM));
    }
    List<Person> chefs = new ArrayList<>();
    chefs.add(new User("Wes", "wh7", ings1));
    chefs.add(new User("Grant", "ggustafs", ings2));
    List<Recipe> recipes = RecipeMatcher.matchRecipes(chefs, dbM);
    assertTrue(recipes.size() == 9);
    // just looking at one recipe here
    Recipe gaz = recipes.get(7);
    assertTrue(gaz.name().equals("Gazpacho"));
    assertTrue(gaz.id().equals("/r/1.6"));
    assertTrue(gaz.shoppingPrice() == 3.31);

    // half the items
    chefs = new ArrayList<>();
    ings1 = new ArrayList<>();
    ings2 = new ArrayList<>();
    double[] amt = {1, 2, 8, 2};
    for (int i = 0; i < 2; i++) {
      ings1.add(new Ingredient(ids[i], amt[i], dbM));
      ings2.add(new Ingredient(ids[i + 2], amt[i + 2], dbM));
    }
    chefs.add(new User("Wes", "wh7", ings1));
    chefs.add(new User("Grant", "ggustafs", ings2));
    recipes = RecipeMatcher.matchRecipes(chefs, dbM);
    assertTrue(recipes.size() == 9);
    gaz = recipes.get(7);
    assertTrue(gaz.name().equals("Gazpacho"));
    assertTrue(gaz.id().equals("/r/1.6"));
    assertTrue(gaz.shoppingPrice() == 3.74);

    // test all of every item
    chefs = new ArrayList<>();
    ings1 = new ArrayList<>();
    ings2 = new ArrayList<>();
    double[] all = {1, 2, 8, 2, 2, 8, 8, 8};
    for (int i = 0; i < 4; i++) {
      ings1.add(new Ingredient(ids[i], all[i], dbM));
      ings2.add(new Ingredient(ids[i + 4], all[i + 4], dbM));
    }
    chefs.add(new User("Wes", "wh7", ings1));
    chefs.add(new User("Grant", "ggustafs", ings2));
    recipes = RecipeMatcher.matchRecipes(chefs, dbM);
    assertTrue(recipes.size() == 9);
    gaz = recipes.get(7);
    assertTrue(gaz.name().equals("Gazpacho"));
    assertTrue(gaz.id().equals("/r/1.6"));
    assertTrue(gaz.shoppingPrice() == 0);
  }

  @Test
  public void percentOne() {
    Recipe rec = new Recipe("test", null);
    List<Ingredient> ingredients = new ArrayList<>();
    double[] need = {10.0, 4.0, 4.0, 2.0, 1.0};
    String[] ids = {"eggs", "butter", "cheese", "onions", "bacon"};
    for (int i = 0; i < 5; i++) {
      ingredients.add(new Ingredient(ids[i], need[i], null));
    }
    assertTrue(rec.percentHave() == 1);
    rec.setIngredients(ingredients);
    assertTrue(rec.percentHave() == 1);
  }

  @Test
  public void percentZero() {
    Recipe rec = new Recipe("test", null);
    List<Ingredient> ingredients = new ArrayList<>();
    double[] amt = {10.0, 4.0, 4.0, 2.0, 1.0};
    String[] ids = {"eggs", "butter", "cheese", "onions", "bacon"};
    for (int i = 0; i < 5; i++) {
      ingredients.add(new Ingredient(ids[i], amt[i], null));
      rec.addToShoppingList(new Ingredient(ids[i], 0, null), amt[i]);
    }
    rec.setIngredients(ingredients);
    assertTrue(rec.percentHave() == 1);
  }
}
