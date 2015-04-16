package edu.brown.cs.cookups;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class People {

	private DBLink db;
	private Map<String, Person> users;
	public People(DBLink db) {
		this.db = db;
		users = new HashMap<String, Person>();
	}
	
	public Person getPersonById(String id) throws SQLException {
		Person p = users.get(id);
		if (p == null) {
			p = db.getPersonById(id);
			users.put(id, p);
		}
		return p;
	}
	
	public void addPerson(String name, String id, List<Ingredient> ingredients) throws SQLException {
		Person p = new User(name, id, ingredients);
		db.addPerson(p);
		users.put(id, p);
	}
	
	public void removePersonById(String id) throws SQLException {
		db.removePersonById(id);
		users.remove(id);
	}
	

}
