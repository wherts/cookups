package edu.brown.cs.cookups.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import edu.brown.cs.cookups.person.PersonManager;
import edu.brown.cs.cookups.schedule.Schedule;

public class CookFriendsHandler implements Route {
  private PersonManager people;
  private DateTimeFormatter formatter;

  public CookFriendsHandler(PersonManager p) {
    people = p;
    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  }

  @Override
  public Object handle(Request request, Response response) {
    QueryParamsMap qm = request.queryMap();

    String auth = qm.value("auth");
    String name = qm.value("name");
    String date = qm.value("date");
    String timeStart = qm.value("time_start");
    String chefs = qm.value("chefs");

    LocalDateTime dateTime = LocalDateTime.parse(timeStart, formatter);
    Schedule s = new Schedule(dateTime, null);
    // Meal meal = new Meal(p, s);
    // meal.setName(name);


    return null;
  }

}
