package edu.brown.cs.cookups.person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.cookups.dating.Suitor;
import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Meal;

public class PersonManager {
  private DBLink db;
  private ConcurrentMap<String, Person> users;
  private ConcurrentMap<String, Suitor> suitors;

  public PersonManager(DBLink db) {
    this.db = db;
    users = new ConcurrentHashMap<String, Person>();
    suitors = new ConcurrentHashMap<String, Suitor>();
  }

  public Person getPersonById(String id) {
    Person p = users.get(id);
    if (p == null) {
      p = db.users().getPersonById(id);
      if (p != null) {
        users.put(id, p);
      }
    }
    return p;
  }

  public List<Person> getPersonsByName(String name)
    throws SQLException {
    return db.users().getPersonsByName(name, this);
  }

  public Person cachePerson(String id, String name,
      List<Ingredient> ingredients) {
    Person person = new User(name, id, ingredients);
    this.users.put(id, person);
    return person;
  }

  public void cacheSuitor(Suitor... suitors) {
    for (Suitor suitor : suitors) {
      this.suitors.put(suitor.person().id(), suitor);
    }
  }

  public Person getPersonIfCached(String id) {
    return this.users.get(id);
  }

  public Person addPerson(String name, String id,
      List<Ingredient> ingredients) throws SQLException {
    Person p = new User(name, id, ingredients);
    db.users().addPerson(p);
    users.put(id, p);
    return p;
  }

  public void removePersonById(String id)
    throws SQLException {
    db.users().removePersonById(id);
    users.remove(id);
  }

  public List<Suitor> getAllSuitors() {
    return new ArrayList<>(suitors.values());
  }

  public void addMealtoPerson(String mealID, String personID) {
    this.db.users().addPersonMeal(personID, mealID);
  }

  public List<Meal> getPersonMeals(Person p) {
    List<String> mealIDs = db.users()
                             .getPersonMealIDs(p.id());
    List<Meal> meals = new ArrayList<Meal>();

    for (String id : mealIDs) {
      Meal m = db.meals().getMealByID(id);
      if (m != null) {
        meals.add(m);
      }

    }
    return meals;
  }

  public void addPersonCuisine(String id, String cuisine) {
    Person p = this.getPersonById(id);
    if (p == null) {
      return;
    }
    p.addCuisine(cuisine);
    this.db.users().updatePersonCuisines(p);
  }
}
