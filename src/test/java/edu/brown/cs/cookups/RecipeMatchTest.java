package edu.brown.cs.cookups;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.User;

public class RecipeMatchTest {
  private DBLink dbL;

  @Before
  public void initialize() {
    try {
      dbL = new DBLink("databases/cookups.sqlite3");
    } catch (ClassNotFoundException | SQLException e) {
      System.err.println("TESTING ERROR");
      fail();
    }
    addRecipes(dbL);
  }

  @Test
  public void recipeCompilation() {
    List<Person> chefs = new ArrayList<>();
    dbL = null;
    try {
      dbL = new DBLink("databases/cookups.sqlite3");
    } catch (ClassNotFoundException | SQLException e) {
      System.err.println("TESTING ERROR");
      fail();
    }
    double[] weights = { 5.0, 9.0, 80.0, 100.0, 999.0 };
    String[] ids = { "one", "two", "three", "four", "five" };
    List<Ingredient> ings1 = new ArrayList<>();
    List<Ingredient> ings2 = new ArrayList<>();
    List<Ingredient> ings3 = new ArrayList<>();
    List<Ingredient> ings4 = new ArrayList<>();
    List<Person> people = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      ings1.add(new Ingredient(ids[i], weights[i], dbL));
      ings2.add(new Ingredient(ids[i], weights[i] * 10, dbL));
      ings3.add(new Ingredient(ids[i],
          weights[i] * 100,
          dbL));
      ings4.add(new Ingredient(ids[i],
          weights[i] * 1000,
          dbL));
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
  public void recipeMatchingOnePerson() throws SQLException {
    List<Person> chefs = new ArrayList<>();
    dbL = null;
    try {
      dbL = new DBLink("databases/cookups.sqlite3");
    } catch (ClassNotFoundException | SQLException e) {
      System.err.println("TESTING ERROR");
      fail();
    }
    List<Ingredient> ings1 = new ArrayList<>();
    ings1.add(new Ingredient("/i/pasta.1", 6, dbL));
    ings1.add(new Ingredient("/i/baking.1", 16, dbL));
    chefs.add(new User("Wes", "/u/wh7", ings1));
    List<Recipe> recipes = RecipeMatcher.matchRecipes(chefs, dbL);
    assertTrue(recipes.size() == 1);
    Recipe r = recipes.get(0);
    assertTrue(recipes.get(0).id().equals("/r/1.5"));
    //peanut butter sesame noodles
    List<Ingredient> toBuy = r.shoppingList();
    assertTrue(toBuy.size() == 7);
    Ingredient pasta = toBuy.get(0);
    assertTrue(pasta.id().equals("/i/pasta.1"));
    assertTrue(pasta.ounces() == 10);
    Ingredient peanutButter = toBuy.get(1);
    assertTrue(peanutButter.id().equals("/i/produce.6"));
    assertTrue(peanutButter.ounces() == 8);
    Ingredient sesameOil = toBuy.get(2);
    assertTrue(sesameOil.id().equals("/i/liquid.5"));
    assertTrue(sesameOil.ounces() == 0.5);
    Ingredient chiliOil = toBuy.get(3);
    assertTrue(chiliOil.id().equals("/i/liquid.6"));
    assertTrue(chiliOil.ounces() == 2);
    Ingredient soySauce = toBuy.get(4);
    assertTrue(soySauce.id().equals("/i/liquid.7"));
    assertTrue(soySauce.ounces() == 2);
    Ingredient rWVinegar = toBuy.get(5);
    assertTrue(rWVinegar.id().equals("/i/liquid.2.3"));
    assertTrue(rWVinegar.ounces() == 4);
    Ingredient water = toBuy.get(6);
    assertTrue(water.id().equals("/i/liquid.0"));
    assertTrue(water.ounces() == 4);
  }

  @Test
  public void recipeMatchingTwoPerson() throws SQLException {
    List<Person> chefs = new ArrayList<>();
    dbL = null;
    try {
      dbL = new DBLink("databases/cookups.sqlite3");
    } catch (ClassNotFoundException | SQLException e) {
      System.err.println("TESTING ERROR");
      fail();
    }
    List<Ingredient> ings1 = new ArrayList<>();
    List<Ingredient> ings2 = new ArrayList<>();
    //wes ingredients
    ings1.add(new Ingredient("/i/produce.3", 32, dbL)); //asparagus
    ings1.add(new Ingredient("/i/produce.4", 32, dbL)); //corn
    ings1.add(new Ingredient("/i/herb.1", 2, dbL)); //dill
    //dylan ingredients
    ings2.add(new Ingredient("/i/dairy.3.3", 16, dbL)); //goat cheese
    ings2.add(new Ingredient("/i/poultry.1", 2, dbL)); //eggs

    chefs.add(new User("Wes", "/u/wh7", ings1));
    chefs.add(new User("Dylan", "/u/dgattey", ings2));
    List<Recipe> recipes = RecipeMatcher.matchRecipes(chefs, dbL);
    assertTrue(recipes.size() == 2);
    dbL.clearCache();
    //bread pudding
    Recipe r1 = recipes.get(0);
    assertTrue(r1.id().equals("/r/1.1"));
    List<Ingredient> toBuy = r1.shoppingList();
    System.out.println(toBuy.size());
    for (Ingredient i : toBuy) {
      System.out.printf("Need: %f of %s%n", i.ounces(), i.id());
    }
//    assertTrue(toBuy.size() == 9);
//
//    Ingredient cream = toBuy.get(0);
//    assertTrue(cream.id().equals("/i/dairy.1"));
//    assertTrue(cream.ounces() == 24);
//
//    Ingredient milk = toBuy.get(1);
//    assertTrue(milk.id().equals("/i/dairy.2.1"));
//    assertTrue(milk.ounces() == 24);
//    
//    Ingredient eggs = toBuy.get(2);
//    assertTrue(eggs.id().equals("/i/poultry.1"));
//    assertTrue(eggs.ounces() == 10);
//    
//    Ingredient sherry = toBuy.get(3);
//    assertTrue(sherry.id().equals("/i/liquid.1"));
//    assertTrue(sherry.ounces() == 8);
//    
//    Ingredient nutmeg = toBuy.get(4);
//    assertTrue(nutmeg.id().equals("/i/spice.1"));
//    assertTrue(Math.floor(nutmeg.ounces() * 100) / 100 == 0.1);
//    
//    Ingredient onions = toBuy.get(5);
//    assertTrue(onions.id().equals("/i/produce.1"));
//    assertTrue(onions.ounces() == 64);
//    
//    Ingredient bread = toBuy.get(6);
//    assertTrue(bread.id().equals("/i/produce.2"));
//    assertTrue(bread.ounces() == 64);
//    
//    Ingredient gruyere = toBuy.get(7);
//    assertTrue(gruyere.id().equals("/i/dairy.3.1"));
//    assertTrue(gruyere.ounces() == 48);
//    
//    Ingredient parm = toBuy.get(8);
//    assertTrue(parm.id().equals("/i/dairy.3.2"));
//    assertTrue(parm.ounces() == 8);
//
//    //Frittata recipe
//    Recipe r2 = recipes.get(1);
//    assertTrue(r2.id().equals("/r/1.4"));
//    toBuy = r2.shoppingList();
//    assertTrue(toBuy.size() == 3);
//
//    Ingredient butter = toBuy.get(0);
//    assertTrue(butter.id().equals("/i/dairy.5"));
//    assertTrue(butter.ounces() == 6);
//    
//    Ingredient scallions = toBuy.get(1);
//    assertTrue(scallions.id().equals("/i/produce.5"));
//    assertTrue(scallions.ounces() == 4);
//    
//    eggs = toBuy.get(2);
//    assertTrue(eggs.id().equals("/i/poultry.1"));
//    assertTrue(eggs.ounces() == 14);
  }

  @Test
  public void noNeedToBuy() throws SQLException {
    List<Person> chefs = new ArrayList<>();
    dbL = null;
    try {
      dbL = new DBLink("databases/cookups.sqlite3");
    } catch (ClassNotFoundException | SQLException e) {
      System.err.println("TESTING ERROR");
      fail();
    }
    dbL.clearCache();
    List<Ingredient> ings1 = new ArrayList<>();
    ings1.add(new Ingredient("/i/produce.10", 24, dbL)); //potatoes
    ings1.add(new Ingredient("/i/dairy.5", 15, dbL)); //butter
    ings1.add(new Ingredient("/i/dairy.2.1", 8, dbL)); //whole milk

    chefs.add(new User("Albie", "/u/ajb7", ings1));
    List<Recipe> recipes = RecipeMatcher.matchRecipes(chefs, dbL);
    assertTrue(recipes.size() == 4);
    Recipe r = recipes.get(1);
    assertTrue(r.id().equals("/r/1.2"));
    assertTrue(r.shoppingList().size() == 0);
  }

  private void addRecipes(DBLink dbL) {
    String instr = "1. Cook pasta al dente "
        + "2. Mix all other ingredients. Add pasta while it is still warm. "
        + "Garnish with shredded carrots, cucumber or scallions";
    if (!dbL.hasRecipe("/r/1.5")) {
      dbL.addRecipe("Peanut Butter Sesame Noodles",
                    "/r/1.5",
                    instr);
      dbL.addRecipeIngredient("/r/1.5", "/i/pasta.1", 16);
      dbL.addRecipeIngredient("/r/1.5", "/i/produce.6", 8);
      dbL.addRecipeIngredient("/r/1.5",
                              "/i/liquid.5",
                              (float) 0.5);
      dbL.addRecipeIngredient("/r/1.5", "/i/liquid.6", 2);
      dbL.addRecipeIngredient("/r/1.5", "/i/liquid.7", 2);
      dbL.addRecipeIngredient("/r/1.5", "/i/liquid.2.3", 4);
      dbL.addRecipeIngredient("/r/1.5", "/i/baking.1", 4);
      dbL.addRecipeIngredient("/r/1.5", "/i/liquid.0", 4);
    }
    instr = "1.Cube Bread and allow to sit over night "
        + "2. Carmelize onions."
        + "3. Set oven to 325F"
        + "4. Combine milk, cream, eggs, sherry, nutmeg, "
        + "salt, pepper, and half the onions in a blender."
        + "Process until combined."
        + "Toss remaining onions with bread and spread over bottom of 9x13 baking dish "
        + "Sprinkle some cheese over bread and put down more bread and cheese "
        + "Top with Parm and pour cream over bread "
        + "Let it sit for at least an hour then bake until custard is set, about 1hr ";
    if (!dbL.hasRecipe("/r/1.1")) {
      dbL.addRecipe("Savory Bread Pudding with Onions and Gruyere",
                    "/r/1.1",
                    instr);
      dbL.addRecipeIngredient("/r/1.1", "/i/dairy.1", 12);
      dbL.addRecipeIngredient("/r/1.1", "/i/dairy.2.1", 12);
      dbL.addRecipeIngredient("/r/1.1", "/i/poultry.1", 6);
      dbL.addRecipeIngredient("/r/1.1", "/i/liquid.1", 4);
      dbL.addRecipeIngredient("/r/1.1",
                              "/i/spice.1",
                              (float) 0.05);
      dbL.addRecipeIngredient("/r/1.1", "/i/produce.1", 32);
      dbL.addRecipeIngredient("/r/1.1", "/i/produce.2", 32);
      dbL.addRecipeIngredient("/r/1.1", "/i/dairy.3.1", 24);
      dbL.addRecipeIngredient("/r/1.1", "/i/dairy.3.2", 4);
    }
    instr = "1. Simmer potatoes in medium saucepan and "
        + "cook over medium heat until tender, about 20 minutes "
        + "2. Drain and let potatoes sit. Combine milk and salt in pan, "
        + "heat milk and mash potatoes into milk."
        + "3. Separately, melt butter and cook over low heat until "
        + "solids are golden brownAdd half of the butter to the potatoes. "
        + "Serve topped with the rest."
        + "Season with salt and pepper to taste";
    if (!dbL.hasRecipe("/r/1.2")) {
      dbL.addRecipe("Brown Butter Mashed Potatoes",
                    "/r/1.2",
                    instr);
      dbL.addRecipeIngredient("/r/1.2", "/i/produce.10", 16);
      dbL.addRecipeIngredient("/r/1.2", "/i/dairy.5", 4);
      dbL.addRecipeIngredient("/r/1.2", "/i/dairy.2.1", 8);
    }
    instr = "Use a pot large enough to hold all the onions, "
        + "place over medium heat and melt butter. "
        + "Add onions, sprinkle with 2t salt, cover, and cook "
        + "until unions are heated through and start to steam. "
        + "Uncover, reduce heat to low and cook, stirring occasionally. "
        + "Season with pepperPreheat oven to 200F, place bread in oven to dry."
        + "When onions are caramelized, add 6C of water, "
        + "raise heat to high and simmer the soup, then lower heat again. "
        + "Add sherry, add red wine and vinegar to balance sweetness and body";
    if (!dbL.hasRecipe("/r/1.3")) {
      dbL.addRecipe("French Onion Soup", "/r/1.3", instr);
      dbL.addRecipeIngredient("/r/1.3", "/i/dairy.5", 1);
      dbL.addRecipeIngredient("/r/1.3",
                              "/i/produce.1.1",
                              128);
      dbL.addRecipeIngredient("/r/1.3", "/i/produce.2.1", 1);
      dbL.addRecipeIngredient("/r/1.3", "/i/liquid.2.1", 3);
      dbL.addRecipeIngredient("/r/1.3", "/i/liquid.3.1", 2);
      dbL.addRecipeIngredient("/r/1.3", "/i/liquid.2.2", 2);
      dbL.addRecipeIngredient("/r/1.3", "/i/dairy.3.1", 12);
    }

    instr = "Set broiler to high "
        + "Trim asparagus and cut into 1-inch pieces "
        + "Heat 1/2 of the butter in a skillet, sauté asparagus, "
        + "corn and scallionsPut vegetables in a bowl with beaten eggs, "
        + "fold in some of the dillWipe skillet clean, melt "
        + "remaining butter in skillet. Pour eggs/veggies into the "
        + "skillet and top with goat cheeseReduce heat on skillet and "
        + "allow sides to puff up and cookPut skillet under broiler for "
        + "1 min to set the topAllow frittata to cool and garnish with remaining dill";
    if (!dbL.hasRecipe("/r/1.4")) {
      dbL.addRecipe("Asparagus and Goat Cheese Frittata",
                    "/r/1.4",
                    instr);
      dbL.addRecipeIngredient("/r/1.4", "/i/produce.3", 16);
      dbL.addRecipeIngredient("/r/1.4", "/i/produce.4", 12);
      dbL.addRecipeIngredient("/r/1.4", "/i/dairy.5", 3);
      dbL.addRecipeIngredient("/r/1.4", "/i/produce.5", 2);
      dbL.addRecipeIngredient("/r/1.4", "/i/herb.1", 1);
      dbL.addRecipeIngredient("/r/1.4", "/i/poultry.1", 8);
      dbL.addRecipeIngredient("/r/1.4", "/i/dairy.3.3", 7);
    }
  }
}