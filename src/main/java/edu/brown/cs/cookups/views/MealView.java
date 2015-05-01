package edu.brown.cs.cookups.views;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import edu.brown.cs.cookups.db.DBManager;

public class MealView implements TemplateViewRoute {
  private DBManager db;

  public MealView(DBManager db) {
    this.db = db;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    String mealEncoded = request.params(":name");
    String mealName = null;

    try {
      mealName = URLDecoder.decode(mealEncoded, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return null;
  }

}
