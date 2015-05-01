package edu.brown.cs.cookups.views;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class MealView implements TemplateViewRoute {
  public MealView() {}

  @Override
  public ModelAndView handle(Request request, Response response) {
    String mealID = request.params(":mealID");

    return null;
  }

}
