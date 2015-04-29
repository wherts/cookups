package edu.brown.cs.cookups.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.brown.cs.cookups.food.Recipe;

public class RecipeDBLink implements RecipeDB {
  public static final int ID_IDX = 1;
  public static final int NAME_IDX = 2;
  public static final int INGREDIENT_IDX = 2;
  public static final int INGREDIENT_QTY_IDX = 3;
  public static final int RECIPE_TEXT_IDX = 3;
  public static final int QTY_IDX = 3;
  private static final Map<String, Recipe> RECIPE_CACHE = new HashMap<String, Recipe>();
  private final Connection conn;
  private final DBManager db;

  public RecipeDBLink(Connection conn, DBManager db) {
    this.conn = conn;
    this.db = db;
  }

  @Override
  public Recipe getRecipeById(String id) {
    return new Recipe(id, (DBLink) this.db);
  }

  @Override
  public void removeRecipe(String id) {

    String query = "DELETE FROM recipe WHERE id = ?";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      prep.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    query = "DELETE FROM recipe_ingredient WHERE recipe = ?";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      prep.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public Set<Recipe> getRecipesWithIngredient(String id) {
    String query = "SELECT recipe FROM recipe_ingredient WHERE ingredient = ?";
    Set<Recipe> recipes = new HashSet<Recipe>();
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {

        while (rs.next()) {
          recipes.add(cacheRecipe(rs.getString(1)));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return recipes;
  }

  @Override
  public String getRecipeNameByID(String id) {
    String query = "SELECT name FROM recipe WHERE id = ?";
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
  public void addRecipeIngredient(String recipe, String id,
      float qty) {
    String command = "INSERT OR IGNORE INTO recipe_ingredient VALUES (?, ?, ?)";
    try (PreparedStatement prep = conn.prepareStatement(command)) {
      prep.setString(ID_IDX, recipe);
      prep.setString(INGREDIENT_IDX, id);
      prep.setFloat(QTY_IDX, qty);
      prep.addBatch();
      prep.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addRecipe(String name, String id, String text) {
    if (hasRecipe(id)) {
      return;
    }
    String command = "INSERT OR IGNORE INTO recipe VALUES (?, ?, ?)";
    try (PreparedStatement prep = conn.prepareStatement(command)) {
      prep.setString(ID_IDX, id);
      prep.setString(NAME_IDX, name);
      prep.setString(RECIPE_TEXT_IDX, text);
      prep.addBatch();
      prep.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean hasRecipe(String id) {
    boolean toRet = true;
    try {
      String query = "SELECT * FROM recipe WHERE id = ?";
      PreparedStatement prep = conn.prepareStatement(query);
      prep = conn.prepareStatement(query);
      prep.setString(1, id);
      ResultSet rs = prep.executeQuery();
      toRet = rs.next();
      prep.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toRet;
  }

  @Override
  public String getInstructionsByRecipe(String id) {
    String query = "SELECT instructions FROM recipe WHERE id = ?";
    String toReturn = null;
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {
        if (rs.next()) {
          toReturn = rs.getString(1);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toReturn;
  }

  public Recipe cacheRecipe(String id) {
    Recipe recipe = RECIPE_CACHE.get(id);
    if (recipe == null) {
      recipe = new Recipe(id, (DBLink) this.db);
      RECIPE_CACHE.put(id, recipe);
    }
    return recipe.copy();
  }

  public void clearCache() {
    RECIPE_CACHE.clear();
  }

}
