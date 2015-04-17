package edu.brown.cs.cookups;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.Suitor;

public class DateMatcher {
  public static List<Person> match(List<Suitor> suitors, Suitor toFind) {
    List<Person> toReturn = new ArrayList<>();
    PriorityQueue<Suitor> matches = new PriorityQueue<>(new DatingComparator(toFind));
    if (toFind.isRomantic()) { //romantic
      for (Suitor suitor : suitors) {
        if (suitor.equals(toFind)) { //don't match toFind with themself
          continue;
        }
        int compatability = toFind.compatability(suitor);
        if (compatability > 0) {
          matches.add(suitor);
        }
      }
      for (Suitor suitor : matches) {
        toReturn.add(suitor.person());
      }
    } else { //platonic
      for (Suitor suitor : suitors) {
        toReturn.add(suitor.person());
      }
    }
    return toReturn;
  }
  
}
