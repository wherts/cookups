package edu.brown.cs.cookups.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.gson.Gson;

import edu.brown.cs.cookups.RecipeMatcher;
import edu.brown.cs.cookups.food.Meal;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;
import edu.brown.cs.cookups.schedule.Schedule;

public class MealDBLink implements MealDB {
  private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
  private static final int ID_LENGTH = 9;
  private ConcurrentMap<String, Meal> meals = new ConcurrentHashMap<String, Meal>();
  private DBManager db;
  private Connection conn;
  private static final Gson gson = new Gson();

  public MealDBLink(Connection conn, DBManager db) {
    this.db = db;
    this.conn = conn;

  }

  @Override
  public Meal getMealByID(String id, PersonManager people) {
    Meal m = meals.get(id);
    if (m != null) {
      List<Person> chefs = new ArrayList<Person>(m.attending());
      chefs.add(m.host());
      m.clearRecipes();
      List<Recipe> recipes;
      try {
        recipes = RecipeMatcher.matchRecipes(chefs, db);
        for (Recipe r : recipes) {
          m.addRecipe(r);
        }
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      return m;
    }
    String query = "SELECT json FROM meal WHERE id = ?";
    String json = "";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);

      try (ResultSet rs = prep.executeQuery()) {
        while (rs.next()) {
          json = rs.getString(1);
          DBMeal dbMeal = gson.fromJson(json, DBMeal.class);
          m = dbMeal.getMeal(people, db);
          meals.put(id, m);
          return m;
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public String addMeal(Meal meal) {
    String id = getRandID();
    while (hasID(id)) {
      id = getRandID();
    }
    meal.setID(id);
    this.meals.put(id, meal); // caching meal here with new id
    DBMeal m = new DBMeal(meal);
    String command = "INSERT OR IGNORE INTO meal VALUES (?, ?)";
    try (PreparedStatement prep = conn.prepareStatement(command)) {
      prep.setString(1, id);
      prep.setString(2, gson.toJson(m));
      prep.addBatch();
      prep.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return id;
  }

  private boolean hasID(String id) {
    String query = "SELECT * FROM meal WHERE id = ?";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {
        return rs.next();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  private String getRandID() {
    int length = CHARS.length();
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < ID_LENGTH; i++) {
      sb.append(CHARS.charAt(random.nextInt(length)));
    }
    return sb.toString();
  }

  private class DBMeal {
    public final String id;
    public final String hostID;
    public List<String> attending;
    public Schedule schedule;
    public List<String> recipes;
    public String name;

    public DBMeal(Meal m) {
      this.id = m.id();
      this.hostID = m.host().id();
      setAttending(m);
      this.schedule = m.schedule();
      setRecipes(m);
      if (m.name() != null) {
        this.name = m.name();
      }

    }

    private void setAttending(Meal m) {
      if (m.attending() == null || m.attending().isEmpty()) {
        return;
      }
      this.attending = new ArrayList<String>();
      for (Person p : m.attending()) {
        this.attending.add(p.id());
      }
    }

    private void setRecipes(Meal m) {
      if (m.recipes() == null || m.recipes().isEmpty()) {
        return;
      }
      this.recipes = new ArrayList<String>();
      for (Recipe r : m.recipes()) {
        this.recipes.add(r.id());
      }
    }

    public Meal getMeal(PersonManager people, DBManager dbM)
      throws SQLException {
      Person host = people.getPersonById(this.hostID);
      Schedule sched = this.schedule;
      Meal meal = new Meal(host, sched);
      meal.setID(this.id);
      if (this.attending != null) {
        rebuildAttending(this.attending, people, meal);
        List<Person> chefs = new ArrayList<>(meal.attending());
        chefs.add(host);
        List<Recipe> recipes = RecipeMatcher.matchRecipes(chefs,
                                                          dbM);
        for (Recipe r : recipes) {
          meal.addRecipe(r);
        }
      }

      // if (this.recipes != null) {
      // rebuildRecipes(this.recipes, meal, dbM);
      // }
      if (this.name != null) {
        meal.setName(this.name);
      }
      return meal;

    }

    private void rebuildAttending(List<String> uids,
        PersonManager people, Meal meal) {
      for (String uid : uids) {
        meal.addAttending(people.getPersonById(uid));
      }
    }

    private void rebuildRecipes(List<String> rids,
        Meal meal, DBManager dbM) {
      for (String rid : rids) {
        meal.addRecipe(dbM.recipes().getRecipeById(rid));
      }
    }

  }
}
