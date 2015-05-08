package edu.brown.cs.cookups.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.cookups.dating.Suitor;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;

public class ProfileView implements TemplateViewRoute {
  private PersonManager people;

  public ProfileView(PersonManager p) {
    people = p;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    String name = request.params(":name");
    String profileID = name + "@brown.edu";
    Person person = people.getPersonById(profileID);

    if (person == null) {
      response.status(404);
    }

    boolean editable = false;
    String userID = request.cookie("id");
    if (profileID.equals(userID)) {
      editable = true;
    }
    String path = userID.split("@")[0];
    
    Suitor suitor = people.getSuitor(person.id());
    Map<String, Object> variables;
    List<Ingredient> ingredients = people.getPersonIngredientsByID(profileID);
    ParamsWrapper wrapper;
    if (suitor != null) {
      wrapper = new ParamsWrapper(suitor.isGay(),
                                  suitor.isBi(),
                                  suitor.isQueer(),
                                  suitor.isPlatonic(),
                                  50 + (suitor.getGender()/2),
                                  ingredients);
      
    } else {
      wrapper = new ParamsWrapper(false,
                                  false,
                                  true,
                                  false,
                                  50,
                                  ingredients);
    }
    
    variables =
        ImmutableMap.of("name", person.name(),
            "wrapper", wrapper,
            "favCuisines", person.favoriteCuisines(),
            "path", name,
            "editable", editable);
    return new ModelAndView(variables, "profile.ftl");
  }

  public class ParamsWrapper {
    private boolean gay, bi, queer, platonic;
    private int gender;
    private List<Ingredient> ingredients;
    
    private ParamsWrapper(boolean gay,
                          boolean bi,
                          boolean queer,
                          boolean platonic,
                          int gender,
                          List<Ingredient> ings) {
      this.gay = gay;
      this.bi = bi;
      this.queer = queer;
      this.platonic = platonic;
      this.gender = gender;
      this.ingredients = ings;
    }

    public boolean gay() {
      return gay;
    }

    public boolean bi() {
      return bi;
    }

    public boolean queer() {
      return queer;
    }

    public boolean platonic() {
      return platonic;
    }

    public int gender() {
      return gender;
    }

    public List<Ingredient> ingredients() {
      return ingredients;
    }
  }
}
