package edu.brown.cs.cookups.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;
import edu.brown.cs.cookups.person.User;

public class UserDBLink implements UserDB {

  public static final int ID_IDX = 1;
  public static final int NAME_IDX = 2;
  public static final int INGREDIENT_IDX = 2;
  public static final int INGREDIENT_QTY_IDX = 3;
  public static final int EXP_IDX = 4;
  public static final int RECIPE_TEXT_IDX = 3;
  public static final int QTY_IDX = 3;
  private final Connection conn;
  private final DBManager db;

  public UserDBLink(Connection conn, DBManager db) {
    this.conn = conn;
    this.db = db;
  }

  @Override
  public void addUserIngredient(String id, Ingredient i) {
    LocalDateTime time = LocalDateTime.now();
    i.setDateCreated(time);
    addUserIngredient(id, i, time);
  }

  @Override
  public void addUserIngredient(String id, Ingredient i,
      LocalDateTime ldt) {
    String query = "INSERT OR IGNORE INTO user_ingredient VALUES (?, ?, ?, ?)";
    try (PreparedStatement prep = conn.prepareStatement(query)) {

      prep.setString(ID_IDX, id);
      prep.setString(INGREDIENT_IDX, i.id());
      prep.setFloat(INGREDIENT_QTY_IDX, (float) i.ounces());
      prep.setString(EXP_IDX, ldt.toString());
      prep.addBatch();
      prep.executeBatch();
      prep.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Person> getPersonsByName(String name,
      PersonManager people) {
    String query = "SELECT * FROM user WHERE name = ?";
    List<Person> users = new ArrayList<>();
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, name);

      try (ResultSet rs = prep.executeQuery()) {
        if (rs == null) {
          return null;
        }
        String id = "";
        while (rs.next()) {
          id = rs.getString(ID_IDX);
          Person person = people.getPersonIfCached(id);
          if (person == null) {
            List<Ingredient> ingredients = getPersonIngredients(id);
            person = people.cachePerson(id,
                                        rs.getString(NAME_IDX),
                                        ingredients);
            String cuisines = rs.getString(3);
            if (cuisines != null) {
              addPersonCuisines(person, cuisines);
            }
          }
          users.add(person);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return users;
  }

  @Override
  public Person getPersonById(String id) {
    String query = "SELECT * FROM user WHERE id = ?";
    String name = "";
    String cuisines = null;
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {
        if (rs == null) {
          return null;
        }

        while (rs.next()) {
          name = rs.getString(NAME_IDX);
          cuisines = rs.getString(3);
        }
      } catch (SQLException e) {
        e.printStackTrace();
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    List<Ingredient> ingredients = getPersonIngredients(id);
    Person p = new User(name, id, ingredients);
    if (cuisines != null) {
      addPersonCuisines(p, cuisines);
    }
    return p;

  }

  @Override
  public void removePersonById(String id) {
    String query = "DELETE FROM user WHERE id = ?";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      prep.executeUpdate();
      prep.close();
      removePersonIngredients(id);
    } catch (SQLException e) {
      e.printStackTrace();

    }
  }

  @Override
  public void removePersonIngredients(String id) {
    String query = "DELETE FROM user_ingredient WHERE user = ?";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      prep.executeUpdate();
      prep.close();
    } catch (SQLException e) {
      e.printStackTrace();

    }
  }

  @Override
  public void removePersonIngredient(String uid, String iid) {
    String query = "DELETE FROM user_ingredient WHERE user = ? AND ingredient = ?";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, uid);
      prep.setString(2, iid);
      prep.executeUpdate();
      prep.close();
    } catch (SQLException e) {
      e.printStackTrace();

    }
  }

  @Override
  public boolean hasPersonByName(String name) {
    boolean toRet = false;
    String query = "SELECT * FROM user WHERE name = ?";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, name);
      try (ResultSet rs = prep.executeQuery()) {
        toRet = rs.next();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toRet;
  }

  @Override
  public boolean hasPersonByID(String id) {
    String query = "SELECT * FROM user WHERE id = ?";
    boolean toRet = false;
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {
        toRet = rs.next();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toRet;
  }

  public List<Ingredient> getPersonIngredients(String id) {
    String query = "SELECT * FROM user_ingredient WHERE user = ?";
    List<Ingredient> toRet = new ArrayList<>();
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {

        while (rs.next()) {
          String ingredID = rs.getString(INGREDIENT_IDX);
          double qty = rs.getDouble(INGREDIENT_QTY_IDX);
          Ingredient i = new Ingredient(ingredID,
              qty,
              (DBLink) db);
          String dateStr = rs.getString(EXP_IDX);
          LocalDateTime date = LocalDateTime.parse(dateStr);
          i.setDateCreated(date);
          toRet.add(i);
        }
      } catch (SQLException e) {
        e.printStackTrace();
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return toRet;
  }

  @Override
  public void addPerson(Person p) {
    String query = "INSERT OR IGNORE INTO user VALUES (?, ?, ?)";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(ID_IDX, p.id());
      prep.setString(NAME_IDX, p.name());
      List<String> cuisines = p.favoriteCuisines();
      if (cuisines != null && cuisines.size() > 0) {
        StringBuilder sb = new StringBuilder();
        sb.append(cuisines.get(0));
        for (int i = 1; i < cuisines.size(); i++) {
          sb.append("," + cuisines.get(i));
        }
        prep.setString(3, sb.toString());
      }
      prep.addBatch();
      prep.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    for (Ingredient i : p.ingredients()) {
      addUserIngredient(p.id(), i);
    }
  }

  @Override
  public void updatePersonCuisines(Person p) {
    assert (this.hasPersonByID(p.id()));
    List<String> cuisines = p.favoriteCuisines();
    if (cuisines == null || cuisines.isEmpty()) {
      return;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(cuisines.get(0));
    for (int i = 1; i < cuisines.size(); i++) {
      sb.append(", " + cuisines.get(i));
    }
    String update = "UPDATE user SET cuisines = ? WHERE id = ?";
    try (PreparedStatement prep = conn.prepareStatement(update)) {
      prep.setString(1, sb.toString());
      prep.setString(2, p.id());
      prep.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<List<String>> getNamesAndIDs() {
    List<String> ids = new ArrayList<String>();
    List<String> names = new ArrayList<String>();
    String query = "SELECT id, name FROM user";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      try (ResultSet rs = prep.executeQuery()) {
        while (rs.next()) {
          ids.add(rs.getString(1));
          names.add(rs.getString(2));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    List<List<String>> toReturn = new ArrayList<List<String>>();
    toReturn.add(0, ids);
    toReturn.add(1, names);
    return toReturn;
  }

  @Override
  public boolean setPersonPassword(String id,
      String password) {
    if (!this.hasPersonByID(id)) {
      return false;
    }
    String update = "INSERT OR REPLACE INTO authentication VALUES(?,?)";
    try (PreparedStatement prep = conn.prepareStatement(update)) {
      prep.setString(1, id);
      prep.setString(2, password);
      prep.executeUpdate();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return true;
  }

  @Override
  public String getPersonPassword(String id) {
    String query = "SELECT password FROM authentication WHERE id = ?";
    String password = "";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {
        while (rs.next()) {
          password = rs.getString(1);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return password;
  }

  @Override
  public void addPersonMeal(String personID, String mealID) {
    String query = "INSERT INTO user_meal VALUES (?, ?)";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, personID);
      prep.setString(2, mealID);
      prep.addBatch();
      prep.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<String> getPersonMealIDs(String id) {
    List<String> meals = new ArrayList<String>();
    String query = "SELECT meal FROM user_meal WHERE user = ?";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {
        while (rs.next()) {
          meals.add(rs.getString(1));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return meals;
  }

  private void addPersonCuisines(Person p, String cuisineStr) {
    String[] cuisines = cuisineStr.split(",");
    List<String> newCuisines = new ArrayList<String>();
    for (String cuisine : cuisines) {
      newCuisines.add(cuisine.trim());
    }
    p.setCuisines(newCuisines);
  }

}
