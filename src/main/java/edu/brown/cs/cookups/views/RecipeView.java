package edu.brown.cs.cookups.views;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.cookups.RecipeMatcher;
import edu.brown.cs.cookups.db.DBManager;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;

public class RecipeView implements TemplateViewRoute {
  private DBManager db;
  private PersonManager people;

  public RecipeView(DBManager db, PersonManager people) {
    this.db = db;
    this.people = people;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    String[] param = request.params(":id").split("%26");
    String idEncoded = param[0];
    String userID = request.cookie("id");
    String profileLink = "/profile/" + userID.split("@")[0];
    Person p = people.getPersonById(userID);

    String id = null;
    try {
      id = URLDecoder.decode(idEncoded, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    Recipe recipe = db.recipes().getRecipeById(id);
    Map<String, Double> ingredients = new HashMap<String, Double>();
    List<Person> chefs = new ArrayList<>();
    chefs.add(p);

    RecipeMatcher.compileIngredients(chefs, ingredients);
    Set<String> keys = ingredients.keySet();
    RecipeMatcher.buildShoppingList(ingredients, recipe);

    Map<String, Object> variables =
        ImmutableMap.of("recipe", recipe, "path", profileLink);
    return new ModelAndView(variables, "recipe.ftl");
  }

}
