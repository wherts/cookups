package edu.brown.cs.cookups.api;

import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

public class BasicView implements TemplateViewRoute {
  private String path;

  public BasicView(String template) {
    path = template;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    Map<String, Object> variables =
        ImmutableMap.of("title", "Brown Cookups");
    return new ModelAndView(variables, path);
  }
}
