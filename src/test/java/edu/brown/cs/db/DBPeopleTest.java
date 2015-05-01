package edu.brown.cs.db;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Meal;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;
import edu.brown.cs.cookups.person.User;
import edu.brown.cs.cookups.schedule.LatLong;
import edu.brown.cs.cookups.schedule.Schedule;

public class DBPeopleTest {

  public static final String DB_PATH = "databases/tests/testDB.sqlite3";
  private static DBLink db;

  // @BeforeClass
  // public static void setup() {
  // try {
  // db = new DBLink(DB_PATH);
  // db.clearDataBase();
  //
  // } catch (ClassNotFoundException | SQLException e) {
  // // TODO Auto-generated catch block
  // e.printStackTrace();
  // }
  // }

  @Test
  public void addPersonTest()
    throws ClassNotFoundException, SQLException {
    DBLink db = new DBLink(DB_PATH);
    PersonManager people = new PersonManager(db);
    Ingredient i = new Ingredient("i", 1.1, null);
    i.setDateCreated(LocalDateTime.now());
    db.ingredients().defineIngredient("i",
                                      "iodine",
                                      1.0,
                                      "Pantry",
                                      1);
    Person p = people.addPerson("Ronald Reagan",
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
    DBLink db = new DBLink(DB_PATH);
    PersonManager people = new PersonManager(db);
    db.ingredients().defineIngredient("i",
                                      "iodine",
                                      1.0,
                                      "Pantry",
                                      1);
    db.ingredients().defineIngredient("j",
                                      "jorga",
                                      1.0,
                                      "Pantry",
                                      1);
    db.ingredients().defineIngredient("k",
                                      "kilbasa",
                                      1.0,
                                      "Pantry",
                                      1);
    Ingredient i = new Ingredient("i", 1.1, null);
    i.setDateCreated(LocalDateTime.now());
    Ingredient j = new Ingredient("j", 1.1, null);
    j.setDateCreated(LocalDateTime.now());
    Ingredient k = new Ingredient("k", 1.1, null);
    k.setDateCreated(LocalDateTime.now());
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
    DBLink db = new DBLink(DB_PATH);
    Ingredient i = new Ingredient("i", 1.1, db);
    LocalDateTime date = LocalDateTime.now();
    i.setDateCreated(date);
    db.ingredients().defineIngredient("i",
                                      "iodine",
                                      1.0,
                                      "Pantry",
                                      1);
    Person p = new User("Ronald Reagan",
        "ronald@aol.com",
        Arrays.asList(i));
    db.users().addPerson(p);
    PersonManager people = new PersonManager(db);
    Person r = people.getPersonById("ronald@aol.com");
    assertTrue(r.id().equals("ronald@aol.com"));
    assertTrue(r.name().equals("Ronald Reagan"));
    Ingredient result = r.ingredients().get(0);
    assertTrue(result.id().equals(i.id()));
    assertTrue(result.getDateCreated().equals(date));
  }

  @Test
  public void authenticationTest() {
    try {
      DBLink db = new DBLink(DB_PATH);
      db.clearDataBase();
      db.ingredients().defineIngredient("/i/pasta",
                                        "pasta",
                                        1.0,
                                        "pantry",
                                        1);
      Ingredient i = new Ingredient("/i/pasta", 1.0, db);
      i.setDateCreated(LocalDateTime.now());
      db.users().addPerson(new User("Ronald",
          "ronald@aol.com",
          Arrays.asList(i)));
      db.users().setPersonPassword("ronald@aol.com",
                                   "freedom");

      assertTrue("freedom".equals(db.users()
                                    .getPersonPassword("ronald@aol.com")));
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void personMealTest()
    throws ClassNotFoundException, SQLException {
    DBLink db = new DBLink(DB_PATH);
    db.clearDataBase();
    PersonManager people = new PersonManager(db);
    people.addPerson("Wes", "wh7", Arrays.asList());
    Schedule sched = new Schedule(LocalDateTime.now(),
        new LatLong(10, 10));
    Meal m1 = new Meal(new User("Wes",
        "wh7",
        new ArrayList<Ingredient>()), sched);
    m1.addAttending(new User("Grant",
        "ggustafs",
        new ArrayList<Ingredient>()));
    Recipe r1 = new Recipe("r1", null);
    m1.addRecipe(r1);
    String mealID = db.meals().addMeal(m1);
    people.addMealtoPerson(mealID, "wh7");
    assertTrue(m1.equals(db.meals().getMealByID(mealID)));
  }

  @Test
  public void personCuisineTest()
    throws ClassNotFoundException, SQLException {
    DBLink db = new DBLink(DB_PATH);
    db.clearDataBase();
    PersonManager people = new PersonManager(db);
    Person p = people.addPerson("Wes",
                                "wh7",
                                Arrays.asList());
    people.addPersonCuisine("wh7", "Tai");
    people.addPersonCuisine("wh7", "Korean");
    List<String> expected = Arrays.asList("Tai", "Korean");
    List<String> result = p.favoriteCuisines();
    assertTrue(expected.size() == result.size());
    for (String s : expected) {
      assertTrue(result.contains(s));
    }
    result = db.users()
               .getPersonById("wh7")
               .favoriteCuisines();
    assertTrue(expected.size() == result.size());
    for (String s : expected) {
      assertTrue(result.contains(s));
    }
  }

}
