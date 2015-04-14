package edu.brown.cs.cookups;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class RecipeMatchTest {

  @Test
  public void recipeCompilation() {
    RecipeMatcher rM = new RecipeMatcher(null);
    List<Person> chefs = new ArrayList<>();
    double[] weights = {5.0, 9.0, 80.0, 100.0, 999.0};
    String[] ids = {"one", "two", "three", "four", "five"};
    List<IngredientProxy> ings1 = new ArrayList<>();
    List<IngredientProxy> ings2 = new ArrayList<>();
    List<IngredientProxy> ings3 = new ArrayList<>();
    List<IngredientProxy> ings4 = new ArrayList<>();
    List<Person> people = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      ings1.add(new TestIngredientProxy(ids[i], weights[i]));
      ings2.add(new TestIngredientProxy(ids[i], weights[i] * 10));
      ings3.add(new TestIngredientProxy(ids[i], weights[i] * 100));
      ings4.add(new TestIngredientProxy(ids[i], weights[i] * 1000));
    }
    people.add(new TestPerson("Albie", ings1));
    people.add(new TestPerson("Wes", ings2));
    people.add(new TestPerson("Taylor", ings3));
    people.add(new TestPerson("Grant", ings4));
    Map<String, Double> map = new HashMap<>();
    rM.compileIngredients(people, map);
    assertTrue(map.get("one") == 5555.0);
    assertTrue(map.get("two") == 9999.0);
    assertTrue(map.get("three") == 88880.0);
    assertTrue(map.get("four") == 111100.0);
    assertTrue(map.get("five") == 1109889.0);
  }
  
  private class TestPerson implements Person {
    private String name;
    private List<IngredientProxy> ingredients;

    private TestPerson(String name, List<IngredientProxy> ing) {
      this.ingredients = ing;
      this.name = name;
    }

    @Override
    public String name() {
      return name;
    }

    @Override
    public String id() {
      return null;
    }

    @Override
    public List<IngredientProxy> ingredients() {
      return ingredients;
    }

    @Override
    public Double latitude() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Double longitude() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public List<Recipe> favoriteRecipes() {
      // TODO Auto-generated method stub
      return null;
    }
  }
  
  private class TestIngredientProxy implements IngredientProxy {
    private String id;
    private double ounces;

    private TestIngredientProxy(String id, double oz) {
      this.id = id;
      this.ounces = oz;
    }

    @Override
    public String name() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public String id() {
      return id;
    }

    @Override
    public List<Recipe> recipes() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void fill() {
      // TODO Auto-generated method stub
      
    }

    @Override
    public Ingredient value() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public double ounces() {
      return ounces;
    }

    @Override
    public double teaspoons() {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public double expiration() {
      // TODO Auto-generated method stub
      return 0;
    }
  }
}