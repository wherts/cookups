package edu.brown.cs.cookups.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import edu.brown.cs.cookups.person.PersonManager;

public class CookFriendsHandler implements Route {
  private PersonManager people;
  private DateTimeFormatter formatter;

  public CookFriendsHandler(PersonManager p) {
    people = p;
    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  }

  @Override
  public Object handle(Request request, Response response) {
    String id = request.cookie("id");
    QueryParamsMap qm = request.queryMap();

    String name = qm.value("name");
    String date = qm.value("date");
    String timeStart = qm.value("timeStart");
    String timeEnd = qm.value("timeEnd");
    String chefs = qm.value("chefs");


    String start = date + " " + timeStart;
    String end = date + " " + timeEnd;
    System.out.println("name " + name + " timestart " + start + " timeEnd "
        + end);
    System.out.println("chefs " + chefs);
    LocalDateTime dateTimeStart = LocalDateTime.parse(start, formatter);
    LocalDateTime dateTimeEnd = LocalDateTime.parse(end, formatter);

    return null;
  }

}
