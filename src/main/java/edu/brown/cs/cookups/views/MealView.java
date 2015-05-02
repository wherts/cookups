package edu.brown.cs.cookups.views;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;

import edu.brown.cs.cookups.PercentageRanker;
import edu.brown.cs.cookups.ShoppingPriceRanker;
import edu.brown.cs.cookups.TotalPriceRanker;
import edu.brown.cs.cookups.db.DBManager;
import edu.brown.cs.cookups.food.Meal;
import edu.brown.cs.cookups.food.Recipe;

public class MealView implements TemplateViewRoute {
  private DBManager db;

  public MealView(DBManager db) {
    this.db = db;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    String mealEncoded = request.params(":id");
    String mealID = null;

//    String query = request.params(":query").split("\\?")[1];
//    Map<String, String> queryMap = Splitter.on('&').trimResults().withKeyValueSeparator("=").split(query);
//    System.out.println(queryMap);
    
    try {
      mealID = URLDecoder.decode(mealEncoded, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    Meal meal = db.meals().getMealByID(mealID);
//    String sort = queryMap.get("sort");
    String sort = "fancy-des";
    Comparator<Recipe> comp = null;
    switch (sort) {
      case "fancy-des":
        comp = new TotalPriceRanker();
        break;
      case "fancy-asc":
        comp = new TotalPriceRanker().reversed();
        break;
      case "price-des":
        comp = new ShoppingPriceRanker();
        break;
      case "price-asc":
        comp = new ShoppingPriceRanker().reversed();
        break;
      case "fewest-missing":
      default:
        comp = new PercentageRanker();
        
    }
    if (meal == null) {
      response.redirect("/", 404);
    }
    List<Recipe> recipes = meal.recipes();
    Collections.sort(recipes, comp);

    String id = request.cookie("id");
    String profLink = "/profile/" + id.split("@")[0];
    Map<String, Object> variables =
        ImmutableMap.of("meal", meal, "recipes", recipes, "profLink", profLink);
    return new ModelAndView(variables, "meal.ftl");
  }

}
