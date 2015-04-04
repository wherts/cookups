package edu.brown.cs.cookups;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBInit {

  private Connection conn;
  public static final String USERS = "users";

  public DBInit(String db) throws ClassNotFoundException,
      SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDb = "jdbc:sqlite:" + db;

    conn = DriverManager.getConnection(urlToDb);
  }

  public void init() throws SQLException {
    String create = "CREATE TABLE IF NOT EXISTS ";
    String schema = create + USERS;
    execute(schema);

  }

  public void execute(String schema) throws SQLException {
    PreparedStatement prep = conn.prepareStatement(schema);
    prep.execute();
    prep.close();
  }
}
