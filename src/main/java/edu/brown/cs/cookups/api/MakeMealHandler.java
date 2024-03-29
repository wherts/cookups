package edu.brown.cs.cookups.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

import edu.brown.cs.cookups.RecipeMatcher;
import edu.brown.cs.cookups.db.DBManager;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Meal;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;
import edu.brown.cs.cookups.person.User;
import edu.brown.cs.cookups.schedule.LatLong;
import edu.brown.cs.cookups.schedule.Schedule;

public class MakeMealHandler implements Route {
  private PersonManager people;
  private DateTimeFormatter formatter;
  private DBManager dbM;
  private static final Gson GSON = new Gson();

  public MakeMealHandler(PersonManager p, DBManager d) {
    people = p;
    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    this.dbM = d;
  }

  @Override
  public Object handle(Request request, Response response) {

    String id = request.cookie("id");
    String profileLink = id.split("@")[0];
    QueryParamsMap qm = request.queryMap();

    String name = qm.value("name"); // name of meal
    String date = qm.value("date"); // date of meal
    String timeStart = qm.value("timeStart"); // start time
    String timeEnd = qm.value("timeEnd"); // not required
    String location = qm.value("location");
    String chefs = qm.value("chefs");

    String start = date + " " + timeStart;

    LocalDateTime dateTimeStart = LocalDateTime.parse(start,
                                                      formatter);
    LocalDateTime dateTimeEnd = null;
    if (timeEnd.length() > 0) { // if they scheduled an end time
      String end = date + " " + timeEnd;
      dateTimeEnd = LocalDateTime.parse(end, formatter);
      Duration duration = Duration.between(dateTimeStart.toLocalTime(),
                                           dateTimeEnd.toLocalTime());
      if (duration.isNegative()) {
        dateTimeEnd = LocalDateTime.of(dateTimeEnd.plusDays(1)
                                                  .toLocalDate(),
                                       dateTimeEnd.toLocalTime());
      }
    }
    
    Schedule sched = new Schedule(dateTimeStart, null);
    
    if (location != "") {
      String[] locArray = location.split(",");
      Double lat = Double.parseDouble(locArray[0]);
      Double lng = Double.parseDouble(locArray[1]);
      LatLong loc = new LatLong(lat, lng);
      sched.changeLocation(loc);
    }
    
    Person host = people.getPersonById(id);
    Meal newMeal = new Meal((User) host, sched);

    if (newMeal != null && dateTimeEnd != null) { // if endtime scheduled
      newMeal.setEnd(dateTimeEnd);
    }
    // add recipes to meal
    List<Person> attending = new ArrayList<>();
    // need to figure out how to parse out selected names
    String[] ids = splitNames(chefs);
    for (String s : ids) {
      Person person = people.getPersonById(s);
      attending.add(person);
    }

    for (Person a : attending) {
      for (Ingredient i : a.ingredients()) {
      }
      newMeal.addAttending((User) a);
    }

    if (!attending.contains(host)) {
      attending.add(host); // adding host here
    }

    List<Recipe> toCook = new ArrayList<>();
    try {
      toCook = RecipeMatcher.matchRecipes(attending, dbM);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    for (Recipe r : toCook) {
      for (Ingredient i : r.shoppingList()) {
      }
      newMeal.addRecipe(r);
    }
    for (Recipe r : newMeal.recipes()) {
    }
    newMeal.setName(name);

    String mealID = dbM.meals().addMeal(newMeal);
    for (Person p : newMeal.attending()) {
      dbM.users().addPersonMeal(p.id(), mealID);
    }
    dbM.users().addPersonMeal(id, mealID);

    String mealLink = null;
    try {
      mealLink = "/meal/"
          + URLEncoder.encode(mealID, "UTF-8") + "/default";
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return GSON.toJson(mealLink);
  }

  private String[] splitNames(String n) {
    String[] names = n.trim().split(",");
    return names;
  }
}
