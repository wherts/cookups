package edu.brown.cs.cookups.dating;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import edu.brown.cs.cookups.person.Person;

public class DateMatcher {
  public static List<Person> match(List<Suitor> suitors, Suitor toFind) {
    List<Person> toReturn = new ArrayList<>();
    PriorityQueue<Suitor> matches =
        new PriorityQueue<>(new DatingComparator(toFind));
    if (toFind.isRomantic()) { // romantic
      for (Suitor suitor : suitors) {
        if (suitor.equals(toFind)) { // don't match toFind with themself
          continue;
        }
        boolean compatable = toFind.compatability(suitor);
        if (compatable) {
          matches.add(suitor);
        }
      }
      while (!matches.isEmpty()) {
        toReturn.add(matches.poll().person());
      }
    } else { // platonic
      for (Suitor suitor : suitors) {
        if (suitor.equals(toFind)) { // don't match toFind with themself
          continue;
        }
        toReturn.add(suitor.person());
      }
    }
    return toReturn;
  }
}
