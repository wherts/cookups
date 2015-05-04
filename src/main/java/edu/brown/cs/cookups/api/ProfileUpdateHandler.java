package edu.brown.cs.cookups.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

import edu.brown.cs.cookups.db.DBManager;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.person.PersonManager;

public class ProfileUpdateHandler implements Route {
  private static final Gson GSON = new Gson();
  private DBManager dbM;
  private PersonManager people;

  public ProfileUpdateHandler(DBManager d, PersonManager p) {
    this.dbM = d;
    this.people = p;
  }

  @Override
  public Object handle(Request request, Response response) {
    String uid = request.params(":id") + "@brown.edu";
    QueryParamsMap qm = request.queryMap();

    // BufferedImage img = null;
    // try {
    // URL url = new URL(qm.value("image"));
    // System.out.println(url.getPath());
    // img = ImageIO.read(url);
    // } catch (IOException e) {
    // e.printStackTrace();
    // System.err.println("Couldn't open");
    // }
    // try {
    // // retrieve image
    // BufferedImage bi = img;
    // File outputfile = new File("saved.png");
    // ImageIO.write(bi, "png", outputfile);
    // } catch (IOException e) {
    // e.printStackTrace();
    // System.err.println("Couldn't save");
    // }

    String ingredients = qm.value("personIngs");
    String cuisines = qm.value("favorites");
    // breaking down ingredients mapping
    String trimmed = trimEnds(ingredients);
    String[] pairs = trimmed.split(",");
    Map<String, Double> map = new HashMap<>();
    for (int i = 0; i < pairs.length; i++) {
      String[] split = pairs[i].split(":");
      String key = split[0];
      String value = split[1];
      map.put(trimEnds(key), Double.parseDouble(value));
    }
    List<Ingredient> newIngredients = new ArrayList<>();
    for (String key : map.keySet()) {
      String id = dbM.ingredients()
                     .getIngredientIDByName(key);
      Ingredient i = new Ingredient(id, map.get(key), dbM);
      newIngredients.add(i);
    }

    // parsing out cuisine information
    String[] splitCuisines = cuisines.split(",");
    List<String> newCuisines = new ArrayList<>();
    for (String s : splitCuisines) {
      newCuisines.add(s);
    }

    // System.out.println(newIngredients);
    // System.out.println(newCuisines);

    people.updateUser(uid, newIngredients, newCuisines);

    return new Object();
  }

  private String trimEnds(String str) {
    return str.substring(1, str.length() - 1);
  }
}
