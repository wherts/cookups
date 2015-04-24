package edu.brown.cs.food;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Recipe;

public class RecipeTest {

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
  public void testName() {
    Recipe r = new Recipe("/r/1.1", null);
    assert(r.name() == null);
    r.setName("recipe one");
    assertTrue(r.name().equals("recipe one"));
  }

  @Test
  public void testPercentage() {
    Recipe rec = new Recipe("test recipe", null);
    List<Ingredient> toBuy = new ArrayList<>();
    List<Ingredient> ingredients = new ArrayList<>();
    //simple numbers
    double[] totals = {15.0, 8.0, 7.0, 2.0, 3.0};
    double[] shop = {10.0, 4.0, 4.0, 2.0, 1.0};
    String[] ids = {"eggs", "butter", "cheese", "onions", "bacon"};
    for (int i = 0; i < 5; i++) {
      ingredients.add(new Ingredient(ids[i], totals[i], null));
      rec.addToShoppingList(new Ingredient(ids[i], 0, null), shop[i]);
    }
    rec.setIngredients(ingredients);
    assertTrue(rec.percentHave() == 0.6);
    //more complex numbers
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
