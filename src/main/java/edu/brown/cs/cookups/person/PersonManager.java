package edu.brown.cs.cookups.person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

  public boolean hasPersonByID(String id) {
    return db.users().hasPersonByID(id);
  }

  public List<Ingredient> getPersonIngredientsByID(String id) {
    List<Ingredient> toReturn = new ArrayList<>();
    List<Ingredient> dbIngs = db.users()
                                .getPersonIngredients(id);
    for (Ingredient i : dbIngs) {
      if (!i.isExpired()) { // not expired
        toReturn.add(i);
      } else { // expired -> remove from table
        db.users().removePersonIngredient(id, i.id());
      }
    }
    return toReturn;
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
      List<Ingredient> ingredients) {
    Person p = new User(name, id, ingredients);
    db.users().addPerson(p);
    users.put(id, p);
    return p;
  }

  public void addPersonIngredient(String uid, Ingredient i) {
    Person p = this.getPersonById(uid);
    p.ingredients().add(i);
    db.users().addUserIngredient(uid, i);
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
      Meal m = db.meals().getMealByID(id, this);
      if (m != null) {
        meals.add(m);
      }

    }
    return meals;
  }

  public List<String> getPersonCuisines(String id) {
    Person p = this.getPersonById(id);
    return p.favoriteCuisines();
  }

  public void updateUser(String id,
      List<Ingredient> updateIngredients,
      List<String> cuisines) {
    Person p = this.getPersonById(id);
    p.setCuisines(cuisines);
    db.users().updatePersonCuisines(p);

    List<Ingredient> oldIngreds = p.ingredients();
    Map<String, Ingredient> oldIngredMap = p.ingredientsMap();
    List<Ingredient> staticIngreds = new ArrayList<Ingredient>();
    List<Ingredient> newIngreds = new ArrayList<Ingredient>();
    for (Ingredient i : updateIngredients) {
      if (oldIngreds.contains(i)) {
        staticIngreds.add(i);
        Ingredient old = oldIngredMap.get(i.id());

        i.setDateCreated(old.getDateCreated());

      } else {
        newIngreds.add(i);
      }

    }
    db.users().removePersonIngredients(id);
    for (Ingredient i : staticIngreds) {
      db.users().addUserIngredient(id,
                                   i,
                                   i.getDateCreated());

    }
    for (Ingredient i : newIngreds) {
      db.users().addUserIngredient(id, i);

    }
    p.setIngredients(db.users().getPersonIngredients(id));
  }

  public Suitor getSuitor(String id) {
    return this.suitors.get(id);
  }
}
