package edu.brown.cs.cookups.dating;

import java.util.Comparator;

public class DatingComparator implements Comparator<Suitor> {
  private Suitor toMatch;
  
  public DatingComparator(Suitor ref) {
    toMatch = ref;
  }
  
  @Override
  public int compare(Suitor suitor1, Suitor suitor2) {
    int compatability1 = toMatch.compatability(suitor1);
    int compatability2 = toMatch.compatability(suitor2);
    return Integer.compare(compatability1, compatability2);
  }
}
