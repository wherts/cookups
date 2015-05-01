package edu.brown.cs.cookups.dating;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.brown.cs.cookups.food.Recipe;

public class DatingComparator implements Comparator<Suitor> {
  private Suitor toMatch;
  
  public DatingComparator(Suitor ref) {
    toMatch = ref;
  }
  
  @Override
  public int compare(Suitor suitor1, Suitor suitor2) {
    int recipesInCommon1, recipesInCommon2;

    List<String> cuisinesToMatch = new ArrayList<>(toMatch.person().favoriteCuisines());
    List<String> suitorCuisines = new ArrayList<>(suitor1.person().favoriteCuisines());

    cuisinesToMatch.retainAll(suitorCuisines);
    recipesInCommon1 = cuisinesToMatch.size();
    
    cuisinesToMatch = new ArrayList<>(toMatch.person().favoriteCuisines());
    suitorCuisines = new ArrayList<>(suitor2.person().favoriteCuisines());
    
    cuisinesToMatch.retainAll(suitorCuisines);
    recipesInCommon2 = cuisinesToMatch.size();

    int compatability1 = (1 + recipesInCommon1) + (toMatch.sexAppeal(suitor1) + suitor1.sexAppeal(toMatch));
    int compatability2 = (1 + recipesInCommon2) + (toMatch.sexAppeal(suitor2) + suitor2.sexAppeal(toMatch));
    return Integer.compare(compatability2, compatability1);
  }
}
