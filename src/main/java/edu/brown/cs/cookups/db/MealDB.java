package edu.brown.cs.cookups.db;

import edu.brown.cs.cookups.food.Meal;

public interface MealDB {

  public Meal getMealByID(String ID);

  public String addMeal(Meal meal);

}
