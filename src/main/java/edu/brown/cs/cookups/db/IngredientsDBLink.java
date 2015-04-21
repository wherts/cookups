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
    String id = null;
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
    String name = null;
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
  public List<String> getAllIngredientNames() {
    String query = "SELECT name FROM ingredient";
    List<String> names = new ArrayList<>();
    try (PreparedStatement prep = conn.prepareStatement(query)) {
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
          ingredients.add(new Ingredient(ingredient,
              qty,
              (DBLink) this.db));
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
  public void defineIngredient(String id, String name) {
    String command = "INSERT OR IGNORE INTO ingredient VALUES (?, ?)";
    try (PreparedStatement prep = conn.prepareStatement(command)) {
      prep.setString(1, id);
      prep.setString(2, name);
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

}
