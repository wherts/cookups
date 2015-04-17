package edu.brown.cs.cookups;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class RecipeMatchTest {

  @Test
  public void recipeCompilation() {
    List<Person> chefs = new ArrayList<>();
    DBLink dbL = null;
    try {
      dbL = new DBLink("cookups.sqlite3");
    } catch (ClassNotFoundException | SQLException e) {
      System.err.println("TESTING ERROR");
      fail();
    }
    double[] weights = {5.0, 9.0, 80.0, 100.0, 999.0};
    String[] ids = {"one", "two", "three", "four", "five"};
    List<Ingredient> ings1 = new ArrayList<>();
    List<Ingredient> ings2 = new ArrayList<>();
    List<Ingredient> ings3 = new ArrayList<>();
    List<Ingredient> ings4 = new ArrayList<>();
    List<Person> people = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      ings1.add(new Ingredient(ids[i], weights[i], dbL));
      ings2.add(new Ingredient(ids[i], weights[i] * 10, dbL));
      ings3.add(new Ingredient(ids[i], weights[i] * 100, dbL));
      ings4.add(new Ingredient(ids[i], weights[i] * 1000, dbL));
    }
    people.add(new User("Albie", "ajb7", ings1));
    people.add(new User("Wes", "wh7", ings2));
    people.add(new User("Taylor", "tderosa", ings3));
    people.add(new User("Grant", "ggustafs", ings4));
    Map<String, Double> map = new HashMap<>();
    RecipeMatcher.compileIngredients(people, map);
    assertTrue(map.get("one") == 5555.0);
    assertTrue(map.get("two") == 9999.0);
    assertTrue(map.get("three") == 88880.0);
    assertTrue(map.get("four") == 111100.0);
    assertTrue(map.get("five") == 1109889.0);
  }

  @Test
  public void recipeMatching() throws SQLException {
    List<Person> chefs = new ArrayList<>();
    DBLink dbL = null;
    try {
      dbL = new DBLink("cookups.sqlite3");
    } catch (ClassNotFoundException | SQLException e) {
      System.err.println("TESTING ERROR");
      fail();
    }
    addRecipes(dbL);
   }

  private void addRecipes(DBLink dbL) throws SQLException {
    String instr = "1. Cook pasta al dente "
        + "2. Mix all other ingredients. Add pasta while it is still warm. "
        + "Garnish with shredded carrots, cucumber or scallions";
    dbL.addRecipe("Peanut Butter Sesame Noodles", "pbsr", instr);
    dbL.addRecipeIngredient("pbsr", "angel hair pasta", 16);
    dbL.addRecipeIngredient("pbsr", "peanut butter", 8);
    dbL.addRecipeIngredient("pbsr", "sesame oil", (float) 0.5);
    dbL.addRecipeIngredient("pbsr", "hot chili oil", 2);
    dbL.addRecipeIngredient("pbsr", "soy sauce", 2);
    dbL.addRecipeIngredient("pbsr", "red-wine vinegar", 4);
    dbL.addRecipeIngredient("pbsr", "light brown sugar", 4);
    dbL.addRecipeIngredient("pbsr", "hot water", 4);
    instr = "1.Cube Bread and allow to sit over night "
        + "2. Set oven to 325F"
        + "2. Combine milk, cream, eggs, sherry, nutmeg, "
        + "salt, pepper, and half the onions in a blender."
        + "Process until combined."
        + "Toss remaining onions with bread and spread over bottom of 9x13 baking dish "
        + "Sprinkle some cheese over bread and put down more bread and cheese "
        + "Top with Parm and pour cream over bread "
        + "Let it sit for at least an hour then bake until custard is set, about 1hr ";
    dbL.addRecipe("Savory Bread Pudding with Onions and Gruyere",
                  "sbp", instr);
    dbL.addRecipeIngredient("sbp", "Double Cream", 12);
    dbL.addRecipeIngredient("sbp", "Milk", 12);
    dbL.addRecipeIngredient("sbp", "Eggs", 6);
    dbL.addRecipeIngredient("sbp", "Sherry", 4); 
    dbL.addRecipeIngredient("sbp", "Nutmeg", (float) 0.05); 
    dbL.addRecipeIngredient("sbp", "Large onions, caramelized", 32);
    dbL.addRecipeIngredient("sbp", "Loaf sandwich bread", 32);
    dbL.addRecipeIngredient("sbp", "Gruyere", 24);
    dbL.addRecipeIngredient("sbp", "Parmensano-Reggiano", 4);
    instr = "1. Simmer potatoes in medium saucepan and "
        + "cook over medium heat until tender, about 20 minutes "
        + "2. Drain and let potatoes sitCombine milk and salt in pan, "
        + "heat milk and mash potatoes into milk."
        + "3. Separately, melt butter and cook over low heat until "
        + "solids are golden brownAdd half of the butter to the potatoes. "
        + "Serve topped with the rest."
        + "Season with salt and pepper to taste";
    dbL.addRecipe("Brown Butter Mashed Potatoes", "bbmp", instr);
    dbL.addRecipeIngredient("bbmp", "potatoes", 16);
    dbL.addRecipeIngredient("bbmp", "butter", 4);
    dbL.addRecipeIngredient("bbmp", "whole milk", 8);
    instr = "";
    dbL.addRecipe("French Onion Soup", "fos", instr);
    dbL.addRecipeIngredient("fos", "Butter", 1);
    dbL.addRecipeIngredient("fos", "Onions, spanish", 128);
    dbL.addRecipeIngredient("fos", "Baguette", 1);
    dbL.addRecipeIngredient("fos", "Sherry Vinegar", 3);
    dbL.addRecipeIngredient("fos"   );
    dbL.addRecipeIngredient("fos"   );
    dbL.addRecipeIngredient("fos"   );
  }
}