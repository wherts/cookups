package edu.brown.cs.cookups;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.gson.Gson;

import edu.brown.cs.autocomplete.Engine;
import edu.brown.cs.autocomplete.Trie;
import edu.brown.cs.cookups.api.AuthFilter;
import edu.brown.cs.cookups.api.AuthValidator;
import edu.brown.cs.cookups.api.Authentication;
import edu.brown.cs.cookups.api.AutocompleteHandler;
import edu.brown.cs.cookups.api.CookupHandler;
import edu.brown.cs.cookups.api.EmailValidator;
import edu.brown.cs.cookups.api.LoginHandler;
import edu.brown.cs.cookups.api.LogoutHandler;
import edu.brown.cs.cookups.api.MakeMealHandler;
import edu.brown.cs.cookups.api.ProfileDataHandler;
import edu.brown.cs.cookups.api.ProfileUpdateHandler;
import edu.brown.cs.cookups.api.SearchEngine;
import edu.brown.cs.cookups.api.SendUsersHandler;
import edu.brown.cs.cookups.api.SignupFilter;
import edu.brown.cs.cookups.api.SignupHandler;
import edu.brown.cs.cookups.dating.SampleSuitors;
import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.person.PersonManager;
import edu.brown.cs.cookups.views.BasicView;
import edu.brown.cs.cookups.views.BrowseView;
import edu.brown.cs.cookups.views.LoginView;
import edu.brown.cs.cookups.views.MealView;
import edu.brown.cs.cookups.views.PersonMealsView;
import edu.brown.cs.cookups.views.ProfileView;
import edu.brown.cs.cookups.views.RecipeView;
import freemarker.template.Configuration;

public class URLHandler {
  private DBLink db;
  private PersonManager people;
  private List<String> userIDs, userNames,
      fridgeIngredients, pantryIngredients, recipeNames,
      recipeIDs;
  private Authentication auth;
  private Engine recipeSearch, peopleSearch;

  private final static Gson GSON = new Gson();

  public URLHandler(DBLink db)
      throws ClassNotFoundException, SQLException {
    this.db = db;
    people = new PersonManager(this.db);
    List<List<String>> userData = db.users()
                                    .getNamesAndIDs();
    userIDs = userData.get(0);
    userNames = userData.get(1);
    fridgeIngredients = db.ingredients()
                          .getAllIngredientNames("Fridge");
    pantryIngredients = db.ingredients()
                          .getAllIngredientNames("Pantry");
    List<List<String>> recipeData = db.recipes()
                                      .getNamesAndIDs();
    recipeIDs = recipeData.get(0);
    recipeNames = recipeData.get(1);

    SampleSuitors.createSuitors(people);

    auth = new Authentication(this.db);

    recipeSearch = new Engine(new Trie(recipeNames));
    peopleSearch = new Engine(new Trie(userNames));
  }

  public void runSparkServer() {
    Spark.externalStaticFileLocation("src/main/resources/static");
    // Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();
    Spark.setPort(3456);

    // Set up authentication filters
    Spark.before("/cookwfriends", new AuthFilter(auth));
    Spark.before("/cook", new AuthFilter(auth));
    Spark.before("/cookup", new AuthFilter(auth));
    Spark.before("/recipe/:id", new AuthFilter(auth));
    Spark.before("/profile/:id", new AuthFilter(auth));
    Spark.before("/allUsers", new AuthFilter(auth));
    Spark.before("/profileData/:id", new AuthFilter(auth));
    Spark.before("/recipe/:id", new AuthFilter(auth));
    Spark.before("/meal/:id/:sortby", new AuthFilter(auth));
    Spark.before("/browse", new AuthFilter(auth));
    Spark.before("/meals", new AuthFilter(auth));
    Spark.before("/signup", new SignupFilter(people));

    // Basic template rendering routes
    Spark.get("/", new LoginView(auth), freeMarker);
    Spark.get("/cookwfriends",
              new BasicView("cookwfriends.ftl"),
              freeMarker);
    Spark.get("/cook",
              new BasicView("cook.ftl"),
              freeMarker);
    Spark.get("/cookup",
              new BasicView("cookup.ftl"),
              freeMarker);
    Spark.get("/browse", new BrowseView(db,
        recipeIDs,
        recipeNames), freeMarker);
    Spark.get("/recipe/:id",
              new RecipeView(db, people),
              freeMarker);
    Spark.get("/meal/:id/:sortby",
              new MealView(db, people),
              freeMarker);
    Spark.get("/meals",
              new PersonMealsView(people),
              freeMarker);
    Spark.get("/profile/:name",
              new ProfileView(people),
              freeMarker);

    // Form handling routes
    Spark.post("/makemeal", new MakeMealHandler(people, db));
    Spark.post("/cookup", new CookupHandler(people));
    Spark.post("/login", new LoginHandler(auth));
    Spark.post("/signup",
               new SignupHandler(auth, people),
               freeMarker);
    Spark.post("/search", new SearchEngine(db,
        people,
        recipeSearch,
        peopleSearch), freeMarker);
    Spark.get("/logout", new LogoutHandler(), freeMarker);
    Spark.post("/authValidator", new AuthValidator(auth));
    Spark.post("/emailValidator",
               new EmailValidator(people));
    // JSON data routes
    Spark.get("/allRecipes",
              new AutocompleteHandler(recipeNames));
    Spark.get("/allUsers", new SendUsersHandler(userNames,
        userIDs));
    Spark.get("/profileData/:id",
              new ProfileDataHandler(recipeNames,
                  fridgeIngredients,
                  pantryIngredients,
                  people));
    Spark.post("/updateProfile/:id",
               new ProfileUpdateHandler(db, people));
    // Spark.post("/meal/:id/:query", new FilterHandler(db, people));

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
}
