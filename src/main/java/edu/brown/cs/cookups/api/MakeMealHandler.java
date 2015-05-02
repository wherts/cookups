package edu.brown.cs.cookups.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
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
    System.out.printf("%s%n", timeEnd);
    String chefs = qm.value("chefs");
    System.out.println(date + " " + timeStart + " " + name + " " + chefs);
    String start = date + " " + timeStart;

    LocalDateTime dateTimeStart = LocalDateTime.parse(start, formatter);
    LocalDateTime dateTimeEnd = null;
    if (timeEnd.length() > 0) { // if they scheduled an end time
      String end = date + " " + timeEnd;
      dateTimeEnd = LocalDateTime.parse(end, formatter);
    }
    Schedule sched = new Schedule(dateTimeStart, null);
    Person host = people.getPersonById(id);
    Meal newMeal = new Meal((User) host, sched);

    if (newMeal == null){
      System.err.println("ERROR: Could create not a meal");
    }

    if (newMeal != null && dateTimeEnd != null) { // if endtime scheduled
      newMeal.setEnd(dateTimeEnd);
    } 
    // add recipes to meal
    List<Person> attending = new ArrayList<>();
    // need to figure out how to parse out selected names
    String[] ids = splitNames(chefs);
    for (String s : ids) {
//      System.out.printf("Looking for %s%n", s);
      Person person = people.getPersonById(s);
      attending.add(person);
    }

    for (Person a : attending) {
//      System.out.printf("Adding: %s to the meal%n", a.name()); //DELTE ME
      for (Ingredient i : a.ingredients()) {
//        System.out.printf("They have %f of %s%n", i.ounces(), i.id()); //DELETE ME
      }
      newMeal.addAttending((User) a);
    }
    Person deleteMe = dbM.users().getPersonById(id); //ADD THIS PERSON TO ATTENDING LIST
//    System.out.printf("%s is hosting%n", id);
//    for (Ingredient i : deleteMe.ingredients()) { //DELTE THIS
//      System.out.printf("They have %f of %s%n", i.ounces(), i.id());
//    }
    attending.add(deleteMe);
    List<Recipe> toCook = new ArrayList<>();
    try {
      toCook = RecipeMatcher.matchRecipes(attending, dbM);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    for (Recipe r : toCook) {
      System.out.printf("Cooking: %s%n", r.name());
      for (Ingredient i : r.shoppingList()) {
        System.out.printf("Need: %f of %s%n", i.ounces(), i.id());
      }
      System.out.printf("They need %f of the recipe%n", r.percentNeed());
      System.out.printf("Completing it would cost %f%n", r.shoppingPrice());
      newMeal.addRecipe(r);
    }
    for (Recipe r : newMeal.recipes()) {
      System.out.printf("Cooking: %s%n", r.id());
      System.out.printf("Need %f%n", r.percentNeed());
      System.out.printf("Cost: %f%n", r.shoppingPrice());
    }
    newMeal.setName(name);

    String mealID = dbM.meals().addMeal(newMeal);
    for (Person p : newMeal.attending()) {
      dbM.users().addPersonMeal(p.id(), mealID);
    }
    dbM.users().addPersonMeal(id, mealID);
    
    String mealLink = null;
    try {
      mealLink = "/meal/" + URLEncoder.encode(mealID, "UTF-8");
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