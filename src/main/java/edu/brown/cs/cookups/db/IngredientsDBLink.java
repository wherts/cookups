package edu.brown.cs.cookups.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs.cookups.food.Ingredient;

public class IngredientsDBLink implements IngredientDB {
  private static final Map<String, String> INGREDIENT_NAME_CACHE = new HashMap<String, String>();
  private final Connection conn;
  private final DBManager db;

  public IngredientsDBLink(Connection conn, DBManager db) {
    this.conn = conn;
    this.db = db;
  }

  @Override
  public String getIngredientIDByName(String name) {
    String query = "SELECT id FROM ingredient WHERE name = ?";
    String id = "";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, name);

      try (ResultSet rs = prep.executeQuery()) {
        while (rs.next()) {
          id = rs.getString(1);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return id;
  }

  @Override
  public String getIngredientNameByID(String id) {
    String query = "SELECT name FROM ingredient WHERE id = ?";
    String name = "";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {
        while (rs.next()) {
          name = rs.getString(1);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return name;
  }

  @Override
  public List<String> getAllIngredientNames(String storage) {
    String query = "SELECT name FROM ingredient WHERE storage = ?";
    List<String> names = new ArrayList<>();
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, storage);
      try (ResultSet rs = prep.executeQuery()) {

        while (rs.next()) {
          names.add(rs.getString(1));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return names;
  }

  @Override
  public List<Ingredient> getIngredientsByRecipe(String id) {
    String query = "SELECT * FROM recipe_ingredient WHERE recipe = ?";
    List<Ingredient> ingredients = new ArrayList<>();
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {

        while (rs.next()) {
          String ingredient = rs.getString(2);
          double qty = rs.getDouble(3);
          Ingredient i = new Ingredient(ingredient,
              qty,
              (DBLink) this.db);
          ingredients.add(i.copy());
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ingredients;
  }

  @Override
  public void defineIngredient(String id, String name,
      double price, String storage, int mins) {
    String command = "INSERT OR IGNORE INTO ingredient VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement prep = conn.prepareStatement(command)) {
      prep.setString(1, id);
      prep.setString(2, name.trim());
      prep.setDouble(3, price);
      prep.setString(4, storage);
      prep.setInt(5, mins);
      prep.addBatch();
      prep.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String ingredientNameCache(String id) {
    String name = INGREDIENT_NAME_CACHE.get(id);
    if (name == null) {
      name = this.getIngredientNameByID(id);
      INGREDIENT_NAME_CACHE.put(id, name);
    }
    return name;
  }

  @Override
  public void clearCache() {
    INGREDIENT_NAME_CACHE.clear();
  }

  @Override
  public Boolean hasIngredient(String id) {
    String query = "SELECT id FROM ingredient WHERE id = ?";
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

  @Override
  public double priceByID(String id) {
    String query = "SELECT price FROM ingredient WHERE id = ?";
    double price = 0;
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {
        while (rs.next()) {
          price = Double.parseDouble(rs.getString(1));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return price;
  }

  @Override
  public String storageByID(String id) {
    String query = "SELECT storage FROM ingredient WHERE id = ?";
    String storage = "";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {
        while (rs.next()) {
          storage = rs.getString(1);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return storage;
  }

  @Override
  public int expirationByID(String id) {
    String query = "SELECT exp FROM ingredient WHERE id = ?";
    int exp = 0;
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {
        while (rs.next()) {
          exp = rs.getInt(1);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return exp;
  }

}
