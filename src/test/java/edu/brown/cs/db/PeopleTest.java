package edu.brown.cs.db;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Test;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;
import edu.brown.cs.cookups.person.User;

public class PeopleTest {

  @Test
  public void addPersonTest()
    throws ClassNotFoundException, SQLException {
    DBLink db = new DBLink("databases/peopleTest.sqlite3");
    PersonManager people = new PersonManager(db);
    Ingredient i = new Ingredient("i", 1.1, null);
    db.ingredients().defineIngredient("i", "iodine");
    people.addPerson("Ronald Reagan",
                     "ronald@aol.com",
                     Arrays.asList(i));
    Person r = people.getPersonById("ronald@aol.com");
    db.users().removePersonById("ronald@aol.com");
    assertTrue(r.id().equals("ronald@aol.com"));
    assertTrue(r.name().equals("Ronald Reagan"));
    assertTrue(r.ingredients().get(0).id().equals("i"));
  }

  @Test
  public void addPersonMultIngredTest()
    throws ClassNotFoundException, SQLException {
    DBLink db = new DBLink("databases/peopleTest.sqlite3");
    PersonManager people = new PersonManager(db);
    db.ingredients().defineIngredient("i", "iodine");
    db.ingredients().defineIngredient("j", "jorga");
    db.ingredients().defineIngredient("k", "kilbasa");
    Ingredient i = new Ingredient("i", 1.1, null);
    Ingredient j = new Ingredient("j", 1.1, null);
    Ingredient k = new Ingredient("k", 1.1, null);
    people.addPerson("Ronald Reagan",
                     "ronald@aol.com",
                     Arrays.asList(i, j, k));
    Person r = people.getPersonById("ronald@aol.com");
    // db.removePersonById("ronald@aol.com");
    assertTrue(r.id().equals("ronald@aol.com"));
    assertTrue(r.name().equals("Ronald Reagan"));
    assertTrue(r.ingredients().contains(i));
    assertTrue(r.ingredients().contains(j));
    assertTrue(r.ingredients().contains(k));
  }

  @Test
  public void getPersonTest()
    throws ClassNotFoundException, SQLException {
    DBLink db = new DBLink("databases/peopleTest.sqlite3");
    Ingredient i = new Ingredient("i", 1.1, null);
    db.ingredients().defineIngredient("i", "iodine");
    Person p = new User("Ronald Reagan",
        "ronald@aol.com",
        Arrays.asList(i));
    db.users().addPerson(p);
    PersonManager people = new PersonManager(db);
    Person r = people.getPersonById("ronald@aol.com");
    db.users().removePersonById("ronald@aol.com");
    assertTrue(r.id().equals("ronald@aol.com"));
    assertTrue(r.name().equals("Ronald Reagan"));
    assertTrue(r.ingredients().get(0).id().equals("i"));
  }

}