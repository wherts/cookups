package edu.brown.cs.cookups.api;

import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.autocomplete.Engine;

public class SearchEngine implements TemplateViewRoute {
  private Engine recipes, people;

  public SearchEngine(Engine recipes, Engine people) {
    this.recipes = recipes;
    this.people = people;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    String profileLink = request.cookie("id").split("@")[1];

    QueryParamsMap qm = request.queryMap();

    String term = qm.value("term");

    List<String> recipeRes = recipes.generate(term);
    List<String> peopleRes = people.generate(term);
    System.out.println(recipeRes);
    System.out.println(peopleRes);
    Map<String, Object> variables =
        ImmutableMap.of("recipeRes", recipeRes, "peopleRes", peopleRes,
            "profLink", profileLink);
    return new ModelAndView(variables, "searchResults.ftl");
  }

}
