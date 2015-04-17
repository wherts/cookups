package edu.brown.cs.cookups;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.cookups.db.DBLink;
import freemarker.template.Configuration;

public class GuiHandler {
  private DBLink db;
  private final static Gson GSON = new Gson();

  public GuiHandler(DBLink db)
      throws ClassNotFoundException,
      SQLException {
    this.db = db;
  }

  public void runSparkServer() {
    Spark.externalStaticFileLocation("src/main/resources/static");
    // Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();
    Spark.setPort(3456);
    // Setup Spark Routes
    Spark.get("/", new LoginView(), freeMarker);
    Spark.post("/login", new LoginRoute());
    Spark.post("signup", new SignUpRoute());
    Spark.get("/cook", new CookView(), freeMarker);
    Spark.get("/cookwfriends", new CookFriendsView(), freeMarker);
    Spark.get("/cookup", new CookUpView(), freeMarker);
    Spark.get("/profile", new ProfileView(), freeMarker);
  }
  
  private static FreeMarkerEngine createEngine() {
	    Configuration config = new Configuration();
	    File templates = new File("src/main/resources/spark/template/freemarker");
	    try {
	      config.setDirectoryForTemplateLoading(templates);
	    } catch (IOException ioe) {
	      System.out.printf("ERROR: Unable use %s for template loading.%n",
	          templates);
	      System.exit(1);
	    }
	    return new FreeMarkerEngine(config);
	  }

  private static class LoginView implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables =
          ImmutableMap.of("title", "Brown Cookups");
      return new ModelAndView(variables, "login.ftl");
    }
  }
  
  private static class CookView implements TemplateViewRoute {
	    @Override
	    public ModelAndView handle(Request req, Response res) {
	      Map<String, Object> variables =
	          ImmutableMap.of("title", "Brown Cookups");
	      return new ModelAndView(variables, "cook.ftl");
	    }
	  }
  
  private static class CookFriendsView implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables =
          ImmutableMap.of("title", "Brown Cookups");
      return new ModelAndView(variables, "cookwfriends.ftl");
    }
  }
  
  private static class CookUpView implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables =
          ImmutableMap.of("title", "Brown Cookups");
      return new ModelAndView(variables, "cookup.ftl");
    }
  }
  
  private static class ProfileView implements TemplateViewRoute {
	    @Override
	    public ModelAndView handle(Request req, Response res) {
	      Map<String, Object> variables =
	          ImmutableMap.of("title", "Brown Cookups");
	      return new ModelAndView(variables, "profile.ftl");
	    }
	  }

  private class LoginRoute implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      QueryParamsMap qm = req.queryMap();

      String username = qm.value("username");
      String password = qm.value("password");

      System.out.println("user: " + username);
      System.out.println("password: " + password);
      return GSON.toJson(null);
    }
  }

  private class SignUpRoute implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
        QueryParamsMap qm = req.queryMap();
        
    	String email = qm.value("email");
    	String username = qm.value("username");
    	String password = qm.value("password");
    	
        System.out.println("email: " + email);
        System.out.println("user: " + username);
        System.out.println("password: " + password);
    	return GSON.toJson(null);
    }
  }
}
