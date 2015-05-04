package edu.brown.cs.cookups.api;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.cookups.person.PersonManager;

public class EmailValidator implements Route {
  private Gson gson = new Gson();
  private PersonManager people;

  public EmailValidator(PersonManager people) {
    this.people = people;
  }

  @Override
  public Object handle(Request req, Response arg1) {
    QueryParamsMap qm = req.queryMap();
    String email = qm.value("email");

    boolean authenticated = people.hasPersonByID(email);
    return gson.toJson(ImmutableMap.of("valid",
                                       authenticated));
  }

}
