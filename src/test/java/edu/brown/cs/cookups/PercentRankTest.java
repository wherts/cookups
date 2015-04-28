package edu.brown.cs.cookups;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Recipe;

public class PercentRankTest {
  private Recipe r1, r2, r3, r4, r5;
  
  @Before
  public void init() {
    r1 = new Recipe("r1", null);
    r2 = new Recipe("r2", null);
    r3 = new Recipe("r3", null);
    r4 = new Recipe("r4", null);
    r5 = new Recipe("r5", null);
    List<Ingredient> ing = new ArrayList<>();
    ing.add(new Ingredient("ten", 10, null));

    r1.setIngredients(ing);
    r1.addToShoppingList(new Ingredient("one", 1.0, null), 1.0);
    
    r2.setIngredients(ing);
    r2.addToShoppingList(new Ingredient("three", 3.0, null), 3.0);
    
    r3.setIngredients(ing);
    r3.addToShoppingList(new Ingredient("five", 5.0, null), 5.0);
    
    r4.setIngredients(ing);
    r4.addToShoppingList(new Ingredient("four", 4.0, null), 4.0);
    
    r5.setIngredients(ing);
    r5.addToShoppingList(new Ingredient("two", 2.0, null), 2.0);
  }

  @Test
  public void rankTwo() {
    List<Recipe> recipes = new ArrayList<>();
    recipes.add(r1);
    recipes.add(r2);
    Collections.sort(recipes, new PercentageRanker());
    assertTrue(recipes.get(0).id().equals("r2"));
    assertTrue(recipes.get(1).id().equals("r1"));
  }

  @Test
  public void rankTen() {
    List<Recipe> recipes = new ArrayList<>();
    recipes.add(r1);
    recipes.add(r2);
    recipes.add(r3);
    recipes.add(r4);
    recipes.add(r5);
    Collections.sort(recipes, new PercentageRanker());
    assertTrue(recipes.get(0).id().equals("r3"));
    assertTrue(recipes.get(1).id().equals("r4"));
    assertTrue(recipes.get(2).id().equals("r2"));
    assertTrue(recipes.get(3).id().equals("r5"));
    assertTrue(recipes.get(4).id().equals("r1"));
  }
}
