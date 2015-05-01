package edu.brown.cs.cookups.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.autocomplete.Engine;
import edu.brown.cs.cookups.db.DBManager;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;

public class SearchEngine implements TemplateViewRoute {
  private Engine recipes, people;
  private DBManager db;
  private PersonManager pm;

  public SearchEngine(DBManager db, PersonManager pm, Engine recipes,
      Engine people) {
    this.recipes = recipes;
    this.people = people;
    this.db = db;
    this.pm = pm;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    String profileLink = request.cookie("id").split("@")[0];

    QueryParamsMap qm = request.queryMap();
    String term = qm.value("term");

    // For all the recipes, find their ID to generate an encoded URL
    List<String> recipeResults = recipes.generate(term);
    Map<String, String> recipeMap = new LinkedHashMap<String, String>();
    for (String r : recipeResults) {
      String recipeID = db.recipes().getRecipeByName(r).name();
      try {
        recipeMap.put(r, "/recipe/" + URLEncoder.encode(recipeID, "UTF-8"));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }

    // Find all people names that start with the prefix, then find all people
    // objects
    List<String> peopleAutcomplete = people.generate(term);
    List<Person> peeps = new ArrayList<>();
    for (String p : peopleAutcomplete) {
      try {
        peeps.addAll(pm.getPersonsByName(p));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    // There may be many people with the same name, account for them
    Map<String, String> peopleMap = new LinkedHashMap<String, String>();
    for (Person p : peeps) {
      peopleMap.put(p.name(), "/profile/" + p.id().split("@")[0]);
    }

    Map<String, Object> variables =
        ImmutableMap
            .of("recipes", recipeMap, "people", peopleMap, "profLink",
                profileLink);
    return new ModelAndView(variables, "searchResults.ftl");
  }

}
