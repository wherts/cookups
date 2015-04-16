package edu.brown.cs.cookups;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBLink {

  private Connection conn;
  public static final String USER = "user(id TEXT, name TEXT)";
  public static final String USER_INGREDIENT = "user_ingredient(user TEXT, id TEXT, qty FLOAT)";
  public static final String INGREDIENT_NAME = "ingredient_name(id TEXT, name TEXT)";
  public static final String RECIPE_INGREDIENT = "recipe_ingredient(recipe TEXT, ingredient TEXT, qty FLOAT)";
  public static final String[] tables = { USER,
      USER_INGREDIENT, INGREDIENT_NAME, RECIPE_INGREDIENT };
  public static final int ID_IDX = 1;
  public static final int NAME_IDX = 2;
  public static final int INGREDIENT_IDX = 2;
  public static final int INGREDIENT_QTY_IDX = 3;

  public DBLink(String db) throws ClassNotFoundException,
      SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDb = "jdbc:sqlite:" + db;
    conn = DriverManager.getConnection(urlToDb);
    init();
  }

  public void init() throws SQLException {
    String create = "CREATE TABLE IF NOT EXISTS ";
    for (String s : tables) {
      String schema = create + s;
      execute(schema);
    }
  }

  public void addUserIngredient(String id, Ingredient i)
    throws SQLException {
    String query = "INSERT OR IGNORE INTO user_ingredient VALUES (?, ?, ?)";
    PreparedStatement prep = conn.prepareStatement(query);
    prep = conn.prepareStatement(query);
    prep.setString(ID_IDX, id);
    prep.setString(INGREDIENT_IDX, i.id());
    prep.setFloat(INGREDIENT_QTY_IDX, (float) i.ounces());
    prep.addBatch();
    prep.executeBatch();
    prep.close();
  }

  public List<Person> getPersonsByName(String name,
      People people) throws SQLException {
    String query = "SELECT * FROM user WHERE name = ?";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, name);
    List<Person> users = new ArrayList<Person>();
    ResultSet rs = prep.executeQuery();
    if (rs == null) {
      return null;
    }
    String id = "";
    while (rs.next()) {
      id = rs.getString(ID_IDX);
      Person person = people.getPersonIfCached(id);
      if (person == null) {
        List<Ingredient> ingredients = getUserIngredients(id);
        person = people.cachePerson(id,
                                    rs.getString(NAME_IDX),
                                    ingredients);
      }
      users.add(person);
    }
    rs.close();
    prep.close();
    return users;
  }

  public Person getPersonById(String id)
    throws SQLException {
    String query = "SELECT * FROM user WHERE id = ?";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, id);
    ResultSet rs = prep.executeQuery();
    if (rs == null) {
      return null;
    }
    String name = "";
    while (rs.next()) {
      name = rs.getString(NAME_IDX);
    }
    rs.close();
    prep.close();
    List<Ingredient> ingredients = getUserIngredients(id);
    return new User(name, id, ingredients);

  }

  public void removePersonById(String id)
    throws SQLException {
    String query = "DELETE FROM user WHERE id = ?";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, id);
    prep.executeUpdate();
    prep.close();
    removePersonIngredients(id);
  }

  private void removePersonIngredients(String id)
    throws SQLException {
    String query = "DELETE FROM user_ingredient WHERE user = ?";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, id);
    prep.executeUpdate();
    prep.close();
  }

  public boolean hasPerson(String name) throws SQLException {
    String query = "SELECT * FROM user WHERE name = ?";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, name);
    ResultSet rs = prep.executeQuery();
    boolean toRet = rs.next();
    rs.close();
    return toRet;
  }


  public List<Ingredient> getUserIngredients(String id)
    throws SQLException {
    String query = "SELECT * FROM user_ingredient WHERE user = ?";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, id);
    ResultSet rs = prep.executeQuery();
    List<Ingredient> toRet = new ArrayList<Ingredient>();
    while (rs.next()) {
      String ingredID = rs.getString(INGREDIENT_IDX);
      double qty = rs.getDouble(INGREDIENT_QTY_IDX);
      toRet.add(new Ingredient(ingredID, qty));
    }

    return toRet;

  }


  public String getIngredientNameByID(String id)
    throws SQLException {
    String query = "SELECT name FROM ingredient_name WHERE id = ?";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, id);
    ResultSet rs = prep.executeQuery();
    String name = null;
    while (rs.next()) {
      name = rs.getString(1);
    }
    rs.close();
    prep.close();
    return name;
  }

  public String getIngredientIDByName(String name)
    throws SQLException {
    String query = "SELECT id FROM ingredient_name WHERE name = ?";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, name);
    ResultSet rs = prep.executeQuery();
    String id = null;
    while (rs.next()) {
      id = rs.getString(1);
    }
    rs.close();
    prep.close();
    return id;
  }

  public List<String> getAllIngredientNames()
    throws SQLException {
    String query = "SELECT name FROM ingredient_name";
    PreparedStatement prep = conn.prepareStatement(query);
    ResultSet rs = prep.executeQuery();
    List<String> names = new ArrayList<String>();
    while (rs.next()) {
      names.add(rs.getString(1));
    }
    rs.close();
    prep.close();
    return names;

  }

  public List<Recipe> getRecipesWithIngredient(String id)
    throws SQLException {
    String query = "SELECT recipe FROM recipe_ingredient WHERE ingredient = ?";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, id);
    ResultSet rs = prep.executeQuery();
    List<Recipe> recipes = new ArrayList<Recipe>();
    while (rs.next()) {
      recipes.add(new Recipe(rs.getString(1)));
    }
    rs.close();
    prep.close();
    return recipes;
  }

  public void addPerson(Person p) throws SQLException {


    String query = "INSERT OR IGNORE INTO user VALUES (?, ?)";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(ID_IDX, p.id());
    prep.setString(NAME_IDX, p.name());
    prep.addBatch();
    prep.executeBatch();
    prep.close();

    for (Ingredient i : p.ingredients()) {
      addUserIngredient(p.id(), i);
    }



  }

  public void execute(String schema) throws SQLException {
    PreparedStatement prep = conn.prepareStatement(schema);
    prep.execute();
    prep.close();
  }
}
