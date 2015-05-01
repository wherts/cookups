package edu.brown.cs.cookups.views;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.cookups.db.DBManager;

public class BrowseView implements TemplateViewRoute {
  private List<String> names, ids;
  private DBManager db;

  public BrowseView(DBManager db, List<String> names, List<String> ids) {
    this.names = names;
    this.ids = ids;
    this.db = db;
  }

  @Override
  /**
   * Handles the view for ranking suggestions from the text input in the gui.
   */
  public ModelAndView handle(final Request req, final Response res) {
    String profileLink = "/profile/" + req.cookie("id").split("@")[0];
    List<List<String>> recipes = db.recipes().getNamesAndIDs();
    Map<String, String> recipeMap = new LinkedHashMap<String, String>();
    for (int i = 0; i < recipes.get(0).size(); i++) {
      try {
        recipeMap.put(recipes.get(1).get(i),
            "/recipe/" + URLEncoder.encode(recipes.get(0).get(i), "UTF-8"));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }

    Map<String, Object> variables =
        new ImmutableMap.Builder<String, Object>()
            .put("recipes", recipeMap)
            .put("profLink", profileLink).build();

    return new ModelAndView(variables, "browse.ftl");
  }
}
