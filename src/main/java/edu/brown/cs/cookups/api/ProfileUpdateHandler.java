package edu.brown.cs.cookups.api;

import java.util.ArrayList;

import spark.QueryParamsMap;
import spark.Spark;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

import edu.brown.cs.cookups.db.DBManager;
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
  	String id = request.params(":id");
  	QueryParamsMap qm = request.queryMap();
  	System.out.println("ID found: " + id);
  	System.out.println(qm.value("personIngs"));
  	//unpack map and put in database
  	return new ArrayList<User>();
  }
}
