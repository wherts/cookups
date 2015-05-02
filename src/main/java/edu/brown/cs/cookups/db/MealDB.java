package edu.brown.cs.cookups.db;

import edu.brown.cs.cookups.food.Meal;
import edu.brown.cs.cookups.person.PersonManager;

public interface MealDB {

  public Meal getMealByID(String ID, PersonManager people);

  public String addMeal(Meal meal);

}
