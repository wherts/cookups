package edu.brown.cs.db;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Test;

import edu.brown.cs.cookups.BasicIngredient;
import edu.brown.cs.cookups.DBLink;
import edu.brown.cs.cookups.Ingredient;
import edu.brown.cs.cookups.People;
import edu.brown.cs.cookups.Person;
import edu.brown.cs.cookups.User;

public class PeopleTest {
	
	@Test
	public void addPersonTest() throws ClassNotFoundException, SQLException {
		DBLink db = new DBLink("db.sqlite3");
		People people = new People(db);
		BasicIngredient i = new BasicIngredient("i", 1.1);
		people.addPerson("Ronald Reagan", "ronald@aol.com", Arrays.asList(i));
		Person r = people.getPersonById("ronald@aol.com");
		db.removePersonById("ronald@aol.com");
		assertTrue(r.id().equals("ronald@aol.com"));
		assertTrue(r.name().equals("Ronald Reagan"));
		assertTrue(r.ingredients().get(0).id().equals("i"));
	}
	
	@Test
	public void addPersonMultIngredTest() throws ClassNotFoundException, SQLException {
		DBLink db = new DBLink("db.sqlite3");
		People people = new People(db);
		BasicIngredient i = new BasicIngredient("i", 1.1);
		BasicIngredient j = new BasicIngredient("j", 1.1);
		BasicIngredient k = new BasicIngredient("k", 1.1);
		people.addPerson("Ronald Reagan", "ronald@aol.com", Arrays.asList(i, j, k));
		Person r = people.getPersonById("ronald@aol.com");
		//db.removePersonById("ronald@aol.com");
		assertTrue(r.id().equals("ronald@aol.com"));
		assertTrue(r.name().equals("Ronald Reagan"));
		assertTrue(r.ingredients().contains(i));
		assertTrue(r.ingredients().contains(j));
		assertTrue(r.ingredients().contains(k));
	}
	
	@Test
	public void getPersonTest() throws ClassNotFoundException, SQLException {
		DBLink db = new DBLink("db.sqlite3");
		BasicIngredient i = new BasicIngredient("i", 1.1);
		Person p = new User("Ronald Reagan", "ronald@aol.com", Arrays.asList(i));
		db.addPerson(p);
		People people = new People(db);
		Person r = people.getPersonById("ronald@aol.com");
		db.removePersonById("ronald@aol.com");
		assertTrue(r.id().equals("ronald@aol.com"));
		assertTrue(r.name().equals("Ronald Reagan"));
		assertTrue(r.ingredients().get(0).id().equals("i"));
	}

}
