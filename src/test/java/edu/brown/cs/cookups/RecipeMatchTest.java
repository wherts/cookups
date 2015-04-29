package edu.brown.cs.cookups;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.db.DBManager;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.User;

public class RecipeMatchTest {
  private DBManager dbM;
  private static final String DB_PATH = "databases/cookups.sqlite3";

  @Before
  public void initialize() {
    File file = new File("databases/ingredients/ingredient.csv");
    String dir = "databases/recipes";
    try {
      dbM = new DBLink(DB_PATH);
      dbM.importIngredients(file);
      dbM.importAllRecipes(dir);
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  @Test
  public void onePerson() throws SQLException {
    List<Ingredient> ing = new ArrayList<>();
    ing.add(new Ingredient("/i/poultry.2", 8, dbM));
    ing.add(new Ingredient("/i/baking.1", 16, dbM));
    List<Person> chefs = new ArrayList<>();
    chefs.add(new User("wh7", "Wes", ing));
    List<Recipe> matched = RecipeMatcher.matchRecipes(chefs, dbM);
    assertTrue(matched.size() == 3);
    Recipe fireChicken = matched.get(0);
    System.out.println(fireChicken.name() + ", " + fireChicken.id());
    assertTrue(true);
  }

//  @Test
//  public void recipeCompilation() {
//    List<Person> chefs = new ArrayList<>();
//    double[] weights = { 5.0, 9.0, 80.0, 100.0, 999.0 };
//    String[] ids = { "one", "two", "three", "four", "five" };
//    List<Ingredient> ings1 = new ArrayList<>();
//    List<Ingredient> ings2 = new ArrayList<>();
//    List<Ingredient> ings3 = new ArrayList<>();
//    List<Ingredient> ings4 = new ArrayList<>();
//    List<Person> people = new ArrayList<>();
//    for (int i = 0; i < 5; i++) {
//      ings1.add(new Ingredient(ids[i], weights[i], dbL));
//      ings2.add(new Ingredient(ids[i], weights[i] * 10, dbL));
//      ings3.add(new Ingredient(ids[i],
//          weights[i] * 100,
//          dbL));
//      ings4.add(new Ingredient(ids[i],
//          weights[i] * 1000,
//          dbL));
//    }
//    people.add(new User("Albie", "ajb7", ings1));
//    people.add(new User("Wes", "wh7", ings2));
//    people.add(new User("Taylor", "tderosa", ings3));
//    people.add(new User("Grant", "ggustafs", ings4));
//    Map<String, Double> map = new HashMap<>();
//    RecipeMatcher.compileIngredients(people, map);
//    assertTrue(map.get("one") == 5555.0);
//    assertTrue(map.get("two") == 9999.0);
//    assertTrue(map.get("three") == 88880.0);
//    assertTrue(map.get("four") == 111100.0);
//    assertTrue(map.get("five") == 1109889.0);
//  }
//
//  @Test
//  public void recipeMatchingOnePerson() throws SQLException {
//    List<Person> chefs = new ArrayList<>();
//    List<Ingredient> ings1 = new ArrayList<>();
//    ings1.add(new Ingredient("/i/pasta.1", 6, dbL));
//    ings1.add(new Ingredient("/i/baking.1", 16, dbL));
//    chefs.add(new User("Wes", "/u/wh7", ings1));
//    List<Recipe> recipes = RecipeMatcher.matchRecipes(chefs,
//                                                      dbL);
//    assertTrue(recipes.size() == 1);
//    Recipe r = recipes.get(0);
//    assertTrue(recipes.get(0).id().equals("/r/1.5"));
//    // peanut butter sesame noodles
//    List<Ingredient> toBuy = r.shoppingList();
//    assertTrue(toBuy.size() == 7);
//    Ingredient pasta = toBuy.get(0);
//    assertTrue(pasta.id().equals("/i/pasta.1"));
//    assertTrue(pasta.ounces() == 10);
//    Ingredient peanutButter = toBuy.get(1);
//    assertTrue(peanutButter.id().equals("/i/produce.6"));
//    assertTrue(peanutButter.ounces() == 8);
//    Ingredient sesameOil = toBuy.get(2);
//    assertTrue(sesameOil.id().equals("/i/liquid.5"));
//    assertTrue(sesameOil.ounces() == 0.5);
//    Ingredient chiliOil = toBuy.get(3);
//    assertTrue(chiliOil.id().equals("/i/liquid.6"));
//    assertTrue(chiliOil.ounces() == 2);
//    Ingredient soySauce = toBuy.get(4);
//    assertTrue(soySauce.id().equals("/i/liquid.7"));
//    assertTrue(soySauce.ounces() == 2);
//    Ingredient rWVinegar = toBuy.get(5);
//    assertTrue(rWVinegar.id().equals("/i/liquid.2.3"));
//    assertTrue(rWVinegar.ounces() == 4);
//    Ingredient water = toBuy.get(6);
//    assertTrue(water.id().equals("/i/liquid.0"));
//    assertTrue(water.ounces() == 4);
//  }
//
//  @Test
//  public void onePersonNoMatches() throws SQLException {
//    List<Person> chefs = new ArrayList<>();
//    List<Recipe> recipes = RecipeMatcher.matchRecipes(chefs, dbL);
//    assertTrue(recipes.size() == 0);
//  }
//
//  @Test
//  public void recipeMatchingTwoPerson() throws SQLException {
//    dbL.clearCache();
//    List<Person> chefs = new ArrayList<>();
//    List<Ingredient> ings1 = new ArrayList<>();
//    List<Ingredient> ings2 = new ArrayList<>();
//    // wes ingredients
//    ings1.add(new Ingredient("/i/produce.3", 32, dbL)); // asparagus
//    ings1.add(new Ingredient("/i/produce.4", 32, dbL)); // corn
//    ings1.add(new Ingredient("/i/herb.1", 2, dbL)); // dill
//    // dylan ingredients
//    ings2.add(new Ingredient("/i/dairy.3.3", 16, dbL)); // goat cheese
//    ings2.add(new Ingredient("/i/poultry.1", 2, dbL)); // eggs
//
//    chefs.add(new User("Wes", "/u/wh7", ings1));
//    chefs.add(new User("Dylan", "/u/dgattey", ings2));
//    List<Recipe> recipes = RecipeMatcher.matchRecipes(chefs,
//                                                      dbL);
//    assertTrue(recipes.size() == 2);
//    // bread pudding
//    Recipe r1 = recipes.get(0);
//    assertTrue(r1.id().equals("/r/1.1"));
//    List<Ingredient> toBuy = r1.shoppingList();
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
//    assertTrue(sherry.id().equals("/i/liquid.2.1"));
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
//    // Frittata recipe
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
//  }
}
