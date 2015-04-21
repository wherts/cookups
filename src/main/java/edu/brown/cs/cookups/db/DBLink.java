package edu.brown.cs.cookups.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBLink implements DBManager {
  private Connection conn;
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
  private final UserDB users;
  private final IngredientDB ingredients;
  private final RecipeDB recipes;

  public DBLink(String db) throws ClassNotFoundException,
      SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDb = "jdbc:sqlite:" + db;
    conn = DriverManager.getConnection(urlToDb);
    init();
    this.users = new UserDBLink(conn, this);
    this.ingredients = new IngredientsDBLink(conn, this);
    this.recipes = new RecipeDBLink(conn, this);
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

  private void execute(String schema) {
    try (PreparedStatement prep = conn.prepareStatement(schema)) {
      prep.execute();
      prep.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void clearCache() {
    this.ingredients.clearCache();
    this.recipes.clearCache();
  }

  @Override
  public UserDB users() {
    return this.users;
  }

  @Override
  public IngredientDB ingredients() {
    return this.ingredients;
  }

  @Override
  public RecipeDB recipes() {
    return this.recipes;
  }

}
