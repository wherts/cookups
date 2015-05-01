package edu.brown.cs.cookups.api;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

import edu.brown.cs.cookups.dating.DateMatcher;
import edu.brown.cs.cookups.dating.Suitor;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;

public class CookupHandler implements Route {
  private static final Gson GSON = new Gson();
  private PersonManager people;
  private DateTimeFormatter formatter;

  public CookupHandler(PersonManager p) {
    people = p;
    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  }

  @Override
  public Object handle(Request request, Response response) {
    QueryParamsMap qm = request.queryMap();
    boolean romantic = Boolean.valueOf(qm.value("romantic"));
    int gender = 2 * (Integer.valueOf(qm.value("gender")) - 50);
    String orientation = qm.value("orientation");
    Suitor.Builder builder;
    List<Person> suitors = new ArrayList<>();
    try {
      builder = new Suitor.Builder(people.getPersonById(request.cookie("id")));
      if (!romantic) {
        builder.setPlatonic();
      } else {
        switch (orientation) {
          case "heterosexual":
            builder.setStraight();
            break;
          case "homosexual":
            builder.setGay();
            break;
          case "bisexual":
            builder.setBi().setStraight().setGay();
            break;
        }
      }
      Suitor suitor = builder.build();
      suitors = DateMatcher.match(people.getAllSuitors(), suitor);
      people.cacheSuitor(suitor);
    } catch (SQLException e) {
      System.err.println("ERROR: No id found.");
    }
    return GSON.toJson(suitors);
  }

}
