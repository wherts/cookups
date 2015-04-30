package edu.brown.cs.cookups.api;

import java.time.format.DateTimeFormatter;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import edu.brown.cs.cookups.person.PersonManager;

public class CookupHandler implements Route {
  private PersonManager people;
  private DateTimeFormatter formatter;

  public CookupHandler(PersonManager p) {
    people = p;
    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  }

  @Override
  public Object handle(Request request, Response response) {
    QueryParamsMap qm = request.queryMap();

    String gender = qm.value("gender");
    String heterosexual = qm.value("heterosexual");


    System.out.println(" gender: " + gender
        + " orientation  " + heterosexual);
    return null;
  }

}
