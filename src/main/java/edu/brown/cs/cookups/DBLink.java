package edu.brown.cs.cookups;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class DBLink {

  private Connection conn;
  private static final Map<String, String> INGREDIENT_NAME_CACHE = new HashMap<String, String>();
  private static final Map<String, Recipe> RECIPE_CACHE = new HashMap<String, Recipe>();
  public static final String USER = "user(id TEXT, name TEXT, PRIMARY KEY (id))";
  public static final String INGREDIENT = "ingredient(id TEXT, name TEXT, PRIMARY KEY(id))";
  public static final String USER_INGREDIENT = "user_ingredient(user TEXT, ingredient TEXT, qty FLOAT)";
  public static final String RECIPE = "recipe(id TEXT, name TEXT, instructions TEXT, PRIMARY KEY(id))";
  public static final String RECIPE_INGREDIENT = "recipe_ingredient(recipe TEXT, ingredient TEXT, qty FLOAT" +
	  						", FOREIGN KEY(recipe) REFERENCES recipe(id) ON DELETE CASCADE ON UPDATE CASCADE)";
	  						/*", FOREIGN KEY(ingredient) REFERENCES ingredient(id) ON DELETE CASCADE ON UPDATE CASCADE)"; */
  public static final String[] TABLE_SCHEMA = { USER,
       INGREDIENT, USER_INGREDIENT, RECIPE, RECIPE_INGREDIENT };
  public static final String[] TABLES = {"user", "ingredient", "user_ingredient", "recipe", "recipe_ingredient"};
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
	  String drop = "DROP TABLE IF EXISTS "; 
	  for (String s : TABLES) {
		  execute(drop + s);
	  }
	  
	  */
	  
	  Statement stat = conn.createStatement();
	  stat.executeUpdate("PRAGMA foreign_keys = ON;");
	  
    String create = "CREATE TABLE IF NOT EXISTS ";
	 
    for (String s : TABLE_SCHEMA) {
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

  public void addRecipe(String name, String id, String text)
    throws SQLException {
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
    }
  }
  
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
  

  public void addRecipeIngredient(String recipe, String id,
      float qty) throws SQLException {
    String command = "INSERT OR IGNORE INTO recipe_ingredient VALUES (?, ?, ?)";
    try (PreparedStatement prep = conn.prepareStatement(command)) {
      prep.setString(ID_IDX, recipe);
      prep.setString(INGREDIENT_IDX, id);
      prep.setFloat(QTY_IDX, qty);
      prep.addBatch();
      prep.executeBatch();
    }
  }

  public List<Person> getPersonsByName(String name,
      PersonManager people) throws SQLException {
    String query = "SELECT * FROM user WHERE name = ?";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, name);
    List<Person> users = new ArrayList<>();
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

  public boolean hasPersonByName(String name) throws SQLException {
    String query = "SELECT * FROM user WHERE name = ?";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, name);
    ResultSet rs = prep.executeQuery();
    boolean toRet = rs.next();
    rs.close();
    return toRet;
  }
  
  public boolean hasPersonByID(String id) throws SQLException {
	    String query = "SELECT * FROM user WHERE id = ?";
	    PreparedStatement prep = conn.prepareStatement(query);
	    prep.setString(1, id);
	    ResultSet rs = prep.executeQuery();
	    boolean toRet = rs.next();
	    rs.close();
	    return toRet;
	  }
  
  

  public List<Ingredient> getUserIngredients(String id) throws SQLException {
    String query = "SELECT * FROM user_ingredient WHERE user = ?";
    PreparedStatement prep = conn.prepareStatement(query);
     prep.setString(1, id);
     ResultSet rs = prep.executeQuery();
     List<Ingredient> toRet = new ArrayList<>();
     while(rs.next()) {
       String ingredID = rs.getString(INGREDIENT_IDX);
       double qty = rs.getDouble(INGREDIENT_QTY_IDX);
       toRet.add(new Ingredient(ingredID, qty, this));
     }
     return toRet;
  }

  public String getIngredientNameByID(String id)
    throws SQLException {
    String query = "SELECT name FROM ingredient WHERE id = ?";
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

  public String getRecipeNameByID(String id)
    throws SQLException {
    String query = "SELECT name FROM recipe WHERE id = ?";
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
    String query = "SELECT id FROM ingredient WHERE name = ?";
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
    String query = "SELECT name FROM ingredient";
    PreparedStatement prep = conn.prepareStatement(query);
    ResultSet rs = prep.executeQuery();
    List<String> names = new ArrayList<>();
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
    List<Recipe> recipes = new ArrayList<>();
    while (rs.next()) {
      recipes.add(cacheRecipe(rs.getString(1)));
    }
    rs.close();
    prep.close();
    return recipes;
  }

  public List<Ingredient> getIngredientsByRecipe(String id)
    throws SQLException {
    String query = "SELECT ingredient FROM recipe_ingredient WHERE recipe = ?";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, id);
    ResultSet rs = prep.executeQuery();
    List<Ingredient> ingredients = new ArrayList<>();
    while (rs.next()) {
      ingredients.add(new Ingredient(rs.getString(INGREDIENT_IDX),
          rs.getDouble(INGREDIENT_QTY_IDX),
          this));
    }
    rs.close();
    prep.close();
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

  public void addRecipe(String name, String instructions,
      Map<String, Double> ingredientIDandQTYs)
    throws SQLException {
    Random rand = new Random();
    int id = rand.nextInt(100000 - 10000) + 10000;
    boolean notUnique = true;
    while (notUnique) {
      String query = "SELECT * FROM recipe WHERE id = ?";
      PreparedStatement prep = conn.prepareStatement(query);
      prep.setString(1, Integer.toString(id));
      ResultSet rs = prep.executeQuery();
      notUnique = rs.next();
      rs.close();
      prep.close();
    }
    String query = "INSERT OR IGNORE INTO recipe VALUES (?, ?, ?)";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, Integer.toString(id));
    prep.setString(2, name);
    prep.setString(3, instructions);
    prep.addBatch();
    prep.executeBatch();
    prep.close();

    query = "INSERT OR IGNORE INTO recipe_ingredient VALUES (?, ?, ?)";
    prep = conn.prepareStatement(query);
    prep.setString(1, Integer.toString(id));
    for (Entry<String, Double> e : ingredientIDandQTYs.entrySet()) {
      prep.setString(2, e.getKey());
      prep.setString(3, e.getValue().toString());
      prep.addBatch();
    }
    prep.executeBatch();
    prep.close();
  }
  
  public void removeRecipe(String id) {
	  try{
	  String query = "DELETE FROM recipe WHERE id = ?";
	    PreparedStatement prep = conn.prepareStatement(query);
	    prep.setString(1, id);
	    prep.executeUpdate();
	   
	   query = "DELETE FROM recipe_ingredient WHERE recipe = ?" ;
	    prep = conn.prepareStatement(query);
	   prep.setString(1, id);
	   prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
  }

  public void addPerson(Person p) throws SQLException {
    String query = "INSERT OR IGNORE INTO user VALUES (?, ?)";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(ID_IDX, p.id());
    prep.setString(NAME_IDX, p.name());
    prep.addBatch();
    prep.executeBatch();
    prep.close();

    for (Ingredient i: p.ingredients()) {
      addUserIngredient(p.id(), i);
    }
  }

  public void execute(String schema) throws SQLException {
    PreparedStatement prep = conn.prepareStatement(schema);
    prep.execute();
    prep.close();
  }
}
