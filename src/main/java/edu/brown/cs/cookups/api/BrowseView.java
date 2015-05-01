package edu.brown.cs.cookups.api;

import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

public class BrowseView implements TemplateViewRoute {
  private List<String> names, ids;

  public BrowseView(List<String> names, List<String> ids) {
    this.names = names;
    this.ids = ids;
  }

  @Override
  /**
   * Handles the view for ranking suggestions from the text input in the gui.
   */
  public ModelAndView handle(final Request req, final Response res) {
    Map<String, Object> variables =
        new ImmutableMap.Builder<String, Object>()
            .put("names", names)
            .put("ids", ids).build();

    return new ModelAndView(variables, "browse.ftl");
  }
}
