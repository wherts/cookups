package edu.brown.cs.cookups.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.QueryParamsMap;
import spark.Spark;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

import edu.brown.cs.cookups.db.DBManager;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.person.PersonManager;
import edu.brown.cs.cookups.person.User;

public class ProfileUpdateHandler implements Route {
  private static final Gson GSON = new Gson();
  private DBManager dbM;
  private PersonManager people;

  public ProfileUpdateHandler(DBManager d, PersonManager p) {
    this.dbM = d;
    this.people = p;
  }

  @Override
  public Object handle(Request request, Response response) {
  	String uid = request.params(":id") + "@brown.edu";
  	QueryParamsMap qm = request.queryMap();
  	String ingredients = qm.value("personIngs");
  	String trimmed = ingredients.substring(1, ingredients.length() - 1);
  	String[] pairs = trimmed.split(",");
  	Map<String, Double> map = new HashMap<>();
  	for (int i = 0; i < pairs.length; i++) {
  	  String[] split = pairs[i].split(":");
  	  String key = split[0];
  	  String value = split[1];
  	  map.put(key.substring(1, key.length() - 1), Double.parseDouble(value));
  	}
  	System.out.println(map);
  	List<Ingredient> newIngredients = new ArrayList<>();
  	for (String key : map.keySet()) {
  	  String id = dbM.ingredients().getIngredientIDByName(key);
  	  Ingredient i = new Ingredient(id, map.get(key), dbM);
  	  newIngredients.add(i);
  	}
  	//ideally just update user ingredients with the list, getting foreign key constraints
  	
  	
  	//unpack map and put in database
  	return new Object();
  }
}
