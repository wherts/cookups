package edu.brown.cs.cookups.api;


import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

import edu.brown.cs.cookups.db.DBManager;
import edu.brown.cs.cookups.person.PersonManager;

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
    return null;
  }

}
