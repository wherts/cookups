package edu.brown.cs.db;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Test;

import edu.brown.cs.cookups.Ingredient;
import edu.brown.cs.cookups.DBLink;
import edu.brown.cs.cookups.Person;
import edu.brown.cs.cookups.User;

public class DBLinkTest {
	
	@Test
	public void addUserTest() {
		try {
			DBLink db = new DBLink("db.sqlite3");
			Ingredient i = new Ingredient("i", 1.1, null);
			Person p = new User("Jerry", "qyrt", Arrays.asList(i));
			db.addPerson(p);
			assertTrue(db.hasPerson("Jerry"));
			db.removePersonById("qyrt");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void getUserByNameTest() {
//		try {
//			DBLink db = new DBLink("db.sqlite3");
//			Ingredient i = new Ingredient("i", 1.1, null);
//			Person p = new User("Jerry", "qyrt", Arrays.asList(i));
//			db.addPerson(p);
//			Person q = db.getsPersonByName("Jerry");
//			db.removePersonById("qyrt");
//			assertTrue(q.id().equals("qyrt"));
//			assertTrue(q.name().equals("Jerry"));
//			assertTrue(q.ingredients().get(0).id().equals("i"));
//		} catch (ClassNotFoundException | SQLException e) {
//			fail();
//		}
//	}
	
	@Test
	public void getUserByIDTest() {
		try {
			DBLink db = new DBLink("db.sqlite3");
			Ingredient i = new Ingredient("i", 1.1, null);
			Person p = new User("Jerry", "qyrt", Arrays.asList(i));
			db.addPerson(p);
			Person q = db.getPersonById("qyrt");
			db.removePersonById("qyrt");
			assertTrue(q.id().equals("qyrt"));
			assertTrue(q.name().equals("Jerry"));
			assertTrue(q.ingredients().get(0).id().equals("i"));
		} catch (ClassNotFoundException | SQLException e) {
			fail();
		}
	}

	@Test
	public void getRecipeWithIngredient() {
	  try {
      DBLink db = new DBLink("cookups.sqlite3");
      Ingredient i = new Ingredient("i", 1.1, null);
      Person p = new User("Jerry", "qyrt", Arrays.asList(i));
      db.addPerson(p);
      Person q = db.getPersonById("qyrt");
      db.removePersonById("qyrt");
      assertTrue(q.id().equals("qyrt"));
      assertTrue(q.name().equals("Jerry"));
      assertTrue(q.ingredients().get(0).id().equals("i"));
    } catch (ClassNotFoundException | SQLException e) {
      fail();
    }
	}

}
