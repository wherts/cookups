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
  private Engine search;

  public SearchEngine(Engine s) {
    search = s;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    QueryParamsMap qm = request.queryMap();

    String term = qm.value("term");

    List<String> suggestions = search.generate(term);

    Map<String, Object> variables =
        ImmutableMap.of("suggestions", suggestions);
    return new ModelAndView(variables, "searchResults.ftl");
  }

}
