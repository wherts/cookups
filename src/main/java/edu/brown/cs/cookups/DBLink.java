package edu.brown.cs.cookups;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBLink {

  private Connection conn;
  private static final Map<String, String> INGREDIENT_NAME_CACHE;
  public static final String USER = "user(id TEXT, name TEXT)";
  public static final String USER_INGREDIENT = "user_ingredient(user TEXT, id TEXT, qty FLOAT)";
  public static final String[] tables = {USER, USER_INGREDIENT};
  public static final int ID_IDX = 1;
  public static final int NAME_IDX = 2;
  public static final int INGREDIENT_IDX = 2;
  public static final int INGREDIENT_QTY_IDX = 3;
  

  public DBLink(String db) throws ClassNotFoundException,
      SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDb = "jdbc:sqlite:" + db;
    conn = DriverManager.getConnection(urlToDb);
    this.INGREDIENT_NAME_CACHE = new HashMap<String, String>();
    init();
  }

  public void init() throws SQLException {
	String create = "CREATE TABLE IF NOT EXISTS ";
	for (String s : tables) {
	    String schema = create + s;
	    execute(schema);
	}
    

  }
  
  public void addUserIngredient(String id, Ingredient i) throws SQLException {
	  String query = "INSERT OR IGNORE INTO user_ingredient VALUES (?, ?, ?)";
	  PreparedStatement prep = conn.prepareStatement(query);
	  prep = conn.prepareStatement(query);
		  prep.setString(ID_IDX, id);
		  prep.setString(INGREDIENT_IDX, i.id());
		  prep.setDouble(INGREDIENT_QTY_IDX, i.ounces());
		  prep.addBatch();
	  prep.executeBatch();
	  prep.close();
  }
  
  public Person getPersonByName(String name) throws SQLException {
	  String query = "SELECT * FROM user WHERE name = ?";
	PreparedStatement prep = conn.prepareStatement(query);
	 prep.setString(1, name);
	 ResultSet rs = prep.executeQuery();
	 if (rs == null) {
		 return null;
	 }
	 String id = "";
	 while (rs.next()) {
	id = rs.getString(ID_IDX);
	 }
	 rs.close();
	 prep.close();
	 List<Ingredient> ingredients = getUserIngredients(id);
	 return new User(name, id, ingredients);
  }
  
  
 public Person getPersonById(String id) throws SQLException {
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
  
  public void removePersonById(String id) throws SQLException {
	  String query = "DELETE FROM user WHERE id = ?";
		PreparedStatement prep = conn.prepareStatement(query);
		 prep.setString(1, id);
		 prep.executeUpdate();
		 prep.close();
		 removePersonIngredients(id);
  }
  
  private void removePersonIngredients(String id) throws SQLException {
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
