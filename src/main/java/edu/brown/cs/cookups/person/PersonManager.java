package edu.brown.cs.cookups.person;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.food.Ingredient;

public class PersonManager {
  private DBLink db;
  private Map<String, Person> users;

  public PersonManager(DBLink db) {
    this.db = db;
    users = new HashMap<String, Person>();
  }

  public Person getPersonById(String id)
    throws SQLException {
    Person p = users.get(id);
    if (p == null) {
      p = db.users().getPersonById(id);
      users.put(id, p);
    }
    return p;
  }

  public List<Person> getPersonsByName(String name)
    throws SQLException {
    return db.users().getPersonsByName(name, this);
  }

  public Person cachePerson(String id, String name,
      List<Ingredient> ingredients) {
    Person person = new User(name, id, ingredients);
    this.users.put(id, person);
    return person;
  }

  public Person getPersonIfCached(String id) {
    return this.users.get(id);
  }

  public void addPerson(String name, String id,
      List<Ingredient> ingredients) throws SQLException {
    Person p = new User(name, id, ingredients);
    db.users().addPerson(p);
    users.put(id, p);
  }

  public void removePersonById(String id)
    throws SQLException {
    db.users().removePersonById(id);
    users.remove(id);
  }
}