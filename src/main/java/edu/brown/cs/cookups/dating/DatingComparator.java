package edu.brown.cs.cookups.dating;

import java.util.Comparator;

public class DatingComparator implements Comparator<Suitor> {
  private Suitor toMatch;
  
  public DatingComparator(Suitor ref) {
    toMatch = ref;
  }
  
  @Override
  public int compare(Suitor suitor1, Suitor suitor2) {
    int compatability1 = toMatch.sexAppeal(suitor1) + suitor1.sexAppeal(toMatch);
    int compatability2 = toMatch.sexAppeal(suitor2) + suitor2.sexAppeal(toMatch);
    return Integer.compare(compatability2, compatability1);
  }
}
