package edu.brown.cs.cookups.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;
import edu.brown.cs.cookups.person.User;

public class DBLink implements DBManager {
  private Connection conn;
  private static final Map<String, String> INGREDIENT_NAME_CACHE = new HashMap<String, String>();
  private static final Map<String, Recipe> RECIPE_CACHE = new HashMap<String, Recipe>();
  public static final String USER = "user(id TEXT, name TEXT, PRIMARY KEY (id))";
  public static final String INGREDIENT = "ingredient(id TEXT, name TEXT, PRIMARY KEY(id))";
  public static final String USER_INGREDIENT = "user_ingredient(user TEXT, ingredient TEXT, qty FLOAT)";
  public static final String RECIPE = "recipe(id TEXT, name TEXT, instructions TEXT, PRIMARY KEY(id))";
  public static final String RECIPE_INGREDIENT = "recipe_ingredient(recipe TEXT, ingredient TEXT, qty FLOAT"
      + ", FOREIGN KEY(recipe) REFERENCES recipe(id) ON DELETE CASCADE ON UPDATE CASCADE)";
  /*
   * ", FOREIGN KEY(ingredient) REFERENCES ingredient(id) ON DELETE CASCADE ON UPDATE CASCADE)"
   * ;
   */
  public static final String[] TABLE_SCHEMA = { USER,
      INGREDIENT, USER_INGREDIENT, RECIPE,
      RECIPE_INGREDIENT };
  public static final String[] TABLES = { "user",
      "ingredient", "user_ingredient", "recipe",
      "recipe_ingredient" };
  public static final int ID_IDX = 1;
  public static final int NAME_IDX = 2;
  public static final int INGREDIENT_IDX = 2;
  public static final int INGREDIENT_QTY_IDX = 3;
  public static final int RECIPE_TEXT_IDX = 3;
  public static final int QTY_IDX = 3;

  public DBLink(String db) throws ClassNotFoundException,
      SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDb = "jdbc:sqlite:" + db;
    conn = DriverManager.getConnection(urlToDb);
    init();
  }

  public void init() throws SQLException {

    /*
     * String drop = "DROP TABLE IF EXISTS "; for (String s : TABLES) {
     * execute(drop + s); }
     */

    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys = ON;");

    String create = "CREATE TABLE IF NOT EXISTS ";

    for (String s : TABLE_SCHEMA) {
      String schema = create + s;
      execute(schema);
    }
  }

  @Override
  public void addUserIngredient(String id, Ingredient i) {
    String query = "INSERT OR IGNORE INTO user_ingredient VALUES (?, ?, ?)";
    try (PreparedStatement prep = conn.prepareStatement(query)) {

      prep.setString(ID_IDX, id);
      prep.setString(INGREDIENT_IDX, i.id());
      prep.setFloat(INGREDIENT_QTY_IDX, (float) i.ounces());
      prep.addBatch();
      prep.executeBatch();
      prep.close();
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
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {
        if (rs == null) {
          return null;
        }

        while (rs.next()) {
          name = rs.getString(NAME_IDX);
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
    return new User(name, id, ingredients);
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

  private void removePersonIngredients(String id)
    throws SQLException {
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

  @Override
  public List<Ingredient> getPersonIngredients(String id) {
    String query = "SELECT * FROM user_ingredient WHERE user = ?";
    List<Ingredient> toRet = new ArrayList<>();
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(1, id);
      try (ResultSet rs = prep.executeQuery()) {

        while (rs.next()) {
          String ingredID = rs.getString(INGREDIENT_IDX);
          double qty = rs.getDouble(INGREDIENT_QTY_IDX);
          toRet.add(new Ingredient(ingredID, qty, this));
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
              this));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ingredients;
  }

  public Recipe cacheRecipe(String id) {
    Recipe recipe = RECIPE_CACHE.get(id);
    if (recipe == null) {
      recipe = new Recipe(id, this);
      RECIPE_CACHE.put(id, recipe);
    }
    return recipe;
  }

  public String ingredientNameCache(String id)
    throws SQLException {
    String name = INGREDIENT_NAME_CACHE.get(id);
    if (name == null) {
      name = this.getIngredientNameByID(id);
      INGREDIENT_NAME_CACHE.put(id, name);
    }
    return name;
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
  public void addPerson(Person p) {
    String query = "INSERT OR IGNORE INTO user VALUES (?, ?)";
    try (PreparedStatement prep = conn.prepareStatement(query)) {
      prep.setString(ID_IDX, p.id());
      prep.setString(NAME_IDX, p.name());
      prep.addBatch();
      prep.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    for (Ingredient i : p.ingredients()) {
      addUserIngredient(p.id(), i);
    }
  }

  private void execute(String schema) {
    try (PreparedStatement prep = conn.prepareStatement(schema)) {
      prep.execute();
      prep.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Recipe getRecipeById(String id) {
    return new Recipe(id, this);
  }

  public void clearCache() {
    RECIPE_CACHE.clear();
    INGREDIENT_NAME_CACHE.clear();
  }

}
