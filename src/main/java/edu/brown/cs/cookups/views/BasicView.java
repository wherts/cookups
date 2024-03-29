package edu.brown.cs.cookups.views;

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
    String id = request.cookie("id");
    String profLink = "/profile/" + id.split("@")[0];
    Map<String, Object> variables =
        ImmutableMap.of("title", "Brown Cookups", "path", profLink);
    return new ModelAndView(variables, path);
  }
}
