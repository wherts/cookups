package edu.brown.cs.cookups.api;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.User;

public class AuthenticationTest {
  public static final String TEST_DB = "databases/tests/AuthTest.sqlite3";

  @BeforeClass
  public static void setup() {
    try {
      DBLink db = new DBLink(TEST_DB);
      db.clearDataBase();
      db.ingredients().defineIngredient("/i/pasta",
                                        "pasta",
                                        1.0,
                                        "pantry");
      Person p = new User("Ronald",
          "ron@aol.com",
          Arrays.asList(new Ingredient("/i/pasta", 1.0, db)));
      db.users().addPerson(p);
      Authentication a = new Authentication(db);
      a.setPassword("ron@aol.com", "freedom");
    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void authPassword() {
    try {
      DBLink db = new DBLink(TEST_DB);
      Authentication a = new Authentication(db);
      assertTrue(a.authenticatePassword("ron@aol.com",
                                        "freedom"));
      assertTrue(!a.authenticatePassword("ron@aol.com",
                                         "tyranny"));
    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }

  @Test
  public void authenticateTest() {
    try {
      DBLink db = new DBLink(TEST_DB);
      Authentication a = new Authentication(db);
      String token = a.getToken("ron@aol.com");
      assertTrue(a.authenticate("ron@aol.com", token));
    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
  }
}
