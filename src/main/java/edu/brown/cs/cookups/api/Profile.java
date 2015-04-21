package edu.brown.cs.cookups.api;

import java.util.List;

import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Meal;

public interface Profile {

  public boolean isTakenUserID(String id);

  public String getUserName(String id);

  public List<Ingredient> getUserIngredients(String id);

  public boolean addIngredientToUser(String userID,
      String ingredientID);

  public List<Meal> getUserMeals(String id);

}
