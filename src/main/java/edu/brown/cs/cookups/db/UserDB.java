package edu.brown.cs.cookups.db;

import java.time.LocalDateTime;
import java.util.List;

import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;

public interface UserDB {

  public void addUserIngredient(String id, Ingredient i);

  public void addUserIngredient(String id, Ingredient i,
      LocalDateTime ldt);

  public void addPerson(Person p);

  public Person getPersonById(String id);

  public List<Ingredient> getPersonIngredients(String id);

  public void removePersonIngredient(String uid, String iid);

  public void removePersonIngredients(String id);

  public void removePersonById(String id);

  public boolean hasPersonByID(String id);

  public boolean hasPersonByName(String name);

  public List<Person> getPersonsByName(String name,
      PersonManager people);

  public List<List<String>> getNamesAndIDs();

  public boolean setPersonPassword(String id,
      String password);

  public String getPersonPassword(String id);

  public void addPersonMeal(String personID, String mealID);

  public List<String> getPersonMealIDs(String id);

  public void updatePersonCuisines(Person p);
}
