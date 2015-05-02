package edu.brown.cs.cookups.api;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.cookups.PercentageRanker;
import edu.brown.cs.cookups.ShoppingPriceRanker;
import edu.brown.cs.cookups.TotalPriceRanker;
import edu.brown.cs.cookups.db.DBManager;
import edu.brown.cs.cookups.food.Meal;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.PersonManager;

public class FilterHandler implements Route {
  private static final Gson GSON = new Gson();
  private DBManager dbM;
  private PersonManager people;

  public FilterHandler(DBManager d, PersonManager p) {
  	this.dbM = d;
  	this.people = p;
  }

  @Override
  public Object handle(Request request, Response response) {
  	String mealEncoded = request.params(":id");
	  String query = request.params(":query");
	  String mealID = null;
	      
    try {
      mealID = URLDecoder.decode(mealEncoded, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    
    Meal meal = dbM.meals().getMealByID(mealID, people);
    if (meal == null) {
      response.redirect("/", 404);
    }
	  //create map of queries
	  Map<String, String> queryMap = Splitter.on('&').trimResults().withKeyValueSeparator("=").split(query);
	  System.out.printf("Making filter request: %s%n", mealID);
	  System.out.println(queryMap); //DELETE ME
	  //pull query type
	  String sort = queryMap.get("sort");
	  Comparator<Recipe> comp = null;
	  switch (sort) { //have to play with reversed()
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
	  List<Recipe> toReturn = meal.recipes();
	  Collections.sort(toReturn, comp);
	  List<Map<String, List<String>>> names = new ArrayList<>();
	  //LIMIT DISPLAY OPTIONS HERE
    return GSON.toJson(toReturn);
  }

}
