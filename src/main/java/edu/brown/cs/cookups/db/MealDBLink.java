package edu.brown.cs.cookups.db;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.InstanceCreator;

import edu.brown.cs.cookups.food.Meal;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.User;
import edu.brown.cs.cookups.schedule.LatLong;
import edu.brown.cs.cookups.schedule.Schedule;

public class MealDBLink implements MealDB {
  private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
  private static final int ID_LENGTH = 9;
  private DBManager db;
  private Connection conn;
  private static final Gson gson = new Gson();

  public MealDBLink(Connection conn, DBManager db) {
    this.db = db;
    this.conn = conn;

  }

  @Override
  public Meal getMealByID(String id) {
    String query = "SELECT json FROM meal WHERE id = ?";
    String json = "";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);

      try (ResultSet rs = prep.executeQuery()) {
        while (rs.next()) {
          json = rs.getString(1);
          return gson.fromJson(json, Meal.class);
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
    String command = "INSERT OR IGNORE INTO meal VALUES (?, ?)";
    try (PreparedStatement prep = conn.prepareStatement(command)) {
      prep.setString(1, id);
      prep.setString(2, gson.toJson(meal));
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

  private class PersonInstanceCreator implements
      InstanceCreator<Person> {
    public Person createInstance(Type type) {
      return new User("?", "?", null);
    }
  }

  private class DBMeal {
    private User host;
    private List<User> attending;
    private LatLong location;
    private Schedule schedule;
    private List<Recipe> recipes;
    private String name;

  }

}
