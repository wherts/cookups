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
    File file = new File("databases/csv/ingredients/ingredient.csv");
    String dir = "databases/csv/recipes";
    try {
      dbM = new DBLink(DB_PATH);
      dbM.importIngredients(file);
      dbM.importAllRecipes(dir);
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
//  
//  @Test
//  public void onePerson() throws SQLException {
//    List<Ingredient> ing = new ArrayList<>();
//    ing.add(new Ingredient("/i/poultry.2", 8, dbM));
//    ing.add(new Ingredient("/i/baking.1", 16, dbM));
//    List<Person> chefs = new ArrayList<>();
//    chefs.add(new User("wh7", "Wes", ing));
//    List<Recipe> matched = RecipeMatcher.matchRecipes(chefs, dbM);
//    assertTrue(matched.size() == 3);
//    Recipe stirFry = matched.get(0);
//    assertTrue(stirFry.name().equals("Chicken Stir Fry with Snap Peas"));
//    assertTrue(stirFry.id().equals("/r/1.9"));
//    assertTrue(stirFry.shoppingList().size() == 7);
//    Ingredient bSoda = stirFry.shoppingList().get(0);
//    assertTrue(bSoda.name().equals("Baking Soda"));
//    assertTrue(bSoda.ounces() == 0.025000);
//    
//    Ingredient cornstarch = stirFry.shoppingList().get(1);
//    assertTrue(cornstarch.name().equals("Cornstarch"));
//    assertTrue(cornstarch.ounces() == 0.050000);
//
//    Ingredient rice = stirFry.shoppingList().get(2);
//    assertTrue(rice.name().equals("Rice"));
//    assertTrue(rice.ounces()== 4.000000);
//
//    Ingredient peanutOil = stirFry.shoppingList().get(3);
//    assertTrue(peanutOil.name().equals("Peanut Oil"));
//    assertTrue(peanutOil.ounces() == 0.080000);
//
//    Ingredient oysterSauce = stirFry.shoppingList().get(4);
//    assertTrue(oysterSauce.name().equals("Oyster Sauce"));
//    assertTrue(oysterSauce.ounces() == 0.080000);
//
//    Ingredient soySauce = stirFry.shoppingList().get(5);
//    assertTrue(soySauce.name().equals("Soy Sauce"));
//    assertTrue(soySauce.ounces() == 1.500000);
//    
//    Ingredient snapPeas = stirFry.shoppingList().get(6);
//    assertTrue(snapPeas.name().equals("Snap Peas"));
//    assertTrue(snapPeas.ounces() == 2.000000);
//
//    Recipe fire = matched.get(1);
//    assertTrue(fire.name().equals("Firecracker Chicken"));
//    assertTrue(fire.id().equals("/r/1.10"));
//    
//    assertTrue(fire.shoppingList().size() == 7);
//    cornstarch = fire.shoppingList().get(0);
//    assertTrue(cornstarch.name().equals("Cornstarch"));
//    assertTrue(cornstarch.ounces() == 4.000000);
//    
//    rice = fire.shoppingList().get(1);
//    assertTrue(rice.name().equals("Rice"));
//    assertTrue(rice.ounces()== 4.000000);
//    
//    Ingredient water = fire.shoppingList().get(2);
//    assertTrue(water.name().equals("Water"));
//    assertTrue(water.ounces() == 0.250000);
//    
//    Ingredient sriracha = fire.shoppingList().get(3);
//    assertTrue(sriracha.name().equals("Sriracha"));
//    assertTrue(sriracha.ounces() == 2.000000);
//    
//    Ingredient cider = fire.shoppingList().get(4);
//    assertTrue(cider.name().equals("Apple Cider Vinegar"));
//    assertTrue(cider.ounces() == 0.025000);
//    
//    Ingredient xvoo = fire.shoppingList().get(5);
//    assertTrue(xvoo.name().equals("XVOO"));
//    assertTrue(xvoo.ounces() == 1.000000);
//    
//    Ingredient eggs = fire.shoppingList().get(6);
//    assertTrue(eggs.name().equals("Eggs"));
//    assertTrue(eggs.ounces() == 1.000000);
//    
//    Recipe noodles = matched.get(2);
//    assertTrue(noodles.name().equals("Peanut Butter Sesame Noodles"));
//    assertTrue(noodles.id().equals("/r/1.5"));
//    
//    assertTrue(noodles.shoppingList().size() == 7);
//    
//    water = noodles.shoppingList().get(0);
//    assertTrue(water.name().equals("Water"));
//    assertTrue(water.ounces() == 1.000000);
//    
//    Ingredient vinegar = noodles.shoppingList().get(1);
//    assertTrue(vinegar.name().equals("Red Wine Vinegar"));
//    assertTrue(vinegar.ounces() == 1.000000);
//    
//    Ingredient sesame = noodles.shoppingList().get(2);
//    assertTrue(sesame.name().equals("Sesame Oil"));
//    assertTrue(sesame.ounces() == 0.500000);
//    
//    Ingredient chili = noodles.shoppingList().get(3);
//    assertTrue(chili.name().equals("Hot Chili Oil"));
//    assertTrue(chili.ounces() == 0.250000);
//    
//    soySauce = noodles.shoppingList().get(4);
//    assertTrue(soySauce.name().equals("Soy Sauce"));
//    assertTrue(soySauce.ounces() == 1.000000);
//    
//    Ingredient pasta = noodles.shoppingList().get(5);
//    assertTrue(pasta.name().equals("Angel Hair Pasta"));
//    assertTrue(pasta.ounces() == 8.000000);
//    
//    Ingredient pB = noodles.shoppingList().get(6);
//    assertTrue(pB.name().equals("Peanut Butter"));
//    assertTrue(pB.ounces() == 2);
//  }
//
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
//      ings1.add(new Ingredient(ids[i], weights[i], dbM));
//      ings2.add(new Ingredient(ids[i], weights[i] * 10, dbM));
//      ings3.add(new Ingredient(ids[i],
//          weights[i] * 100,
//          dbM));
//      ings4.add(new Ingredient(ids[i],
//          weights[i] * 1000,
//          dbM));
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
//
//  @Test
//  public void noMatches() throws SQLException {
//    List<Person> chefs = new ArrayList<>();
//    List<Recipe> recipes = RecipeMatcher.matchRecipes(chefs, dbM);
//    assertTrue(recipes.size() == 0);
//    
//    chefs.add(new User("Wes", "wh7", new ArrayList<Ingredient>()));
//    chefs.add(new User("Albie", "ajb7", new ArrayList<Ingredient>()));
//    chefs.add(new User("Taylor", "tderosa", new ArrayList<Ingredient>()));
//    chefs.add(new User("Grant", "ggustafs", new ArrayList<Ingredient>()));
//    recipes = RecipeMatcher.matchRecipes(chefs, dbM);
//    assertTrue(recipes.size() == 0);
//  }

  @Test
  public void recipeMatchingTwoPerson() throws SQLException {
    List<Person> chefs = new ArrayList<>();
    List<Ingredient> ings1 = new ArrayList<>();
    List<Ingredient> ings2 = new ArrayList<>();
    // wes ingredients
    ings1.add(new Ingredient("/i/produce.3", 32, dbM)); // asparagus
    ings1.add(new Ingredient("/i/produce.4", 32, dbM)); // corn
    ings1.add(new Ingredient("/i/herb.1", 2, dbM)); // dill
    // dylan ingredients
    ings2.add(new Ingredient("/i/dairy.3.3", 16, dbM)); // goat cheese
    ings2.add(new Ingredient("/i/poultry.1", 6, dbM)); // eggs

    chefs.add(new User("Wes", "/u/wh7", ings1));
    chefs.add(new User("Dylan", "/u/dgattey", ings2));
    List<Recipe> recipes = RecipeMatcher.matchRecipes(chefs, dbM);
    assertTrue(recipes.size() == 5);
    for (Recipe r : recipes) {
      System.out.println(r.id() + ", " + r.name());
    }
    // bread pudding
    Recipe breadPudding = recipes.get(2);
    assertTrue(breadPudding.name().equals("Savory Bread Pudding"));
    assertTrue(breadPudding.id().equals("/r/1.1"));
    List<Ingredient> toBuy = breadPudding.shoppingList();
    assertTrue(toBuy.size() == 8);

    Ingredient cream = toBuy.get(0);
    assertTrue(cream.name().equals("Double Cream"));
    assertTrue(cream.ounces() == 6.00000);

    Ingredient milk = toBuy.get(1);
    assertTrue(milk.name().equals("Whole Milk"));
    assertTrue(milk.ounces() == 6.000000);

    Ingredient gruyere = toBuy.get(2);
    assertTrue(gruyere.name().equals("Gruyere"));
    assertTrue(gruyere.ounces() == 12.000000);

    Ingredient parm = toBuy.get(3);
    assertTrue(parm.name().equals("Parmesan"));
    assertTrue(parm.ounces() == 2.000000);

    Ingredient sherry = toBuy.get(4);
    assertTrue(sherry.name().equals("Sherry Vinegar"));
    assertTrue(sherry.ounces() == 1.000000);

    Ingredient onions = toBuy.get(5);
    assertTrue(onions.name().equals("Onion"));
    assertTrue(onions.ounces() == 16.000000);

    Ingredient bread = toBuy.get(6);
    assertTrue(bread.name().equals("Bread"));
    assertTrue(bread.ounces() == 8.000000);

    Ingredient nutmeg = toBuy.get(7);
    assertTrue(nutmeg.name().equals("Nutmeg"));
    assertTrue(nutmeg.ounces() == 0.005000);


    // Frittata recipe
    Recipe frittata = recipes.get(3);
    assertTrue(frittata.name().equals("Asparagus and Goat Cheese Frittata"));
    assertTrue(frittata.id().equals("/r/1.4"));
    toBuy = frittata.shoppingList();
    assertTrue(toBuy.size() == 3);

    Ingredient butter = toBuy.get(0);
    assertTrue(butter.name().equals("Butter"));
    assertTrue(butter.ounces() == 4);

    Ingredient eggs = toBuy.get(1);
    assertTrue(eggs.name().equals("Eggs"));
    assertTrue(eggs.ounces() == 2);
    
    Ingredient scallions = toBuy.get(2);
    assertTrue(scallions.name().equals("Scallions"));
    assertTrue(scallions.ounces() == 4);

    //pizza bianco
    Recipe pizza = recipes.get(4);
    assertTrue(pizza.name().equals("Pizza Bianco with Bacon and Eggs"));
    assertTrue(pizza.id().equals("/r/1.7"));
    toBuy = pizza.shoppingList();
    assertTrue(toBuy.size() == 6);
    for (Ingredient i : toBuy) {
      System.out.printf("Need %f of %s, %s%n", i.ounces(), i.id(), i.name());
    }
    Ingredient dough = toBuy.get(0);
    assertTrue(dough.name().equals("Pizza Dough"));
    assertTrue(dough.ounces() == 32);
    
    parm = toBuy.get(1);
    assertTrue(parm.name().equals("Parmesan"));
    assertTrue(parm.ounces() == 4);
    
    Ingredient mozz = toBuy.get(2);
    assertTrue(mozz.name().equals("Mozzarella"));
    assertTrue(mozz.ounces() == 16);
    
    Ingredient xvoo = toBuy.get(3);
    assertTrue(xvoo.name().equals("XVOO"));
    assertTrue(xvoo.ounces() == 8);
    
    Ingredient bacon = toBuy.get(4);
    System.out.println(bacon.name() + "/");
//    assertTrue(bacon.name().equals("Bacon "));
    assertTrue(bacon.ounces() == 16);
    
    Ingredient arugula = toBuy.get(5);
    assertTrue(arugula.name().equals("Arugula"));
    assertTrue(arugula.ounces() == 8);
  }
}
