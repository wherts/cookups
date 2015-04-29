package edu.brown.cs.autocorrect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/** The Ranker class implements both a standard ranking method and a smart
 * ranking method.
 * @author tderosa */
public class Ranker {
  private Trie trie;
  private HashMap<String, HashMap<String, Trie.Count>> pairs;
  private HashMap<String, Trie.Count> dict;
  private Comp uniComp, biComp;
  private SmartComp sComp;
  private Keyboard keys;
  private static final int NUM_SUG_RETURN = 5;
  private static final int KEY_WEIGHT = 2;
  private static final int LED_WEIGHT = 10;
  private static final double BIGRAM_WEIGHT = .05;

  /** Constructor for the Ranker class.
   * @param t The Trie to base ranking upon */
  public Ranker(Trie t) {
    trie = t;
    pairs = trie.pairs();
    dict = trie.dict();
    uniComp = new Comp(dict);
    biComp = null;
    sComp = null;
    keys = new Keyboard();
  }

  /** Smart ranking function. Ranking first my keyboard distance between the
   * suggestion and original word, and then by standard ranking.
   * @param suggestions A list of Strings
   * @param original The original word to be Autocorrected
   * @param prev The previous word from the input or null if none exists
   * @return returns a list of sorted words */
  public List<String> smartRank(List<String> suggestions, String original,
      String prev) {
    List<String> sorted = new ArrayList<String>();
    if (suggestions.contains(original)) {
      suggestions.remove(original);
      sorted.add(original);
    }

    HashMap<String, Integer> keyDistMap = new HashMap<String, Integer>();
    for (String s : suggestions) {
      String[] split = s.split(" ");
      int dist = (int) Math.floor(keyDist(s, original));
      keyDistMap.put(s, dist);
    }

    HashMap<String, Integer> ledDistMap = new HashMap<String, Integer>();
    Generator ledCalc = new Generator(trie, 0);
    for (String s : suggestions) {
      ledDistMap.put(s, ledCalc.levDist(original, s));
    }

    if (prev != null) {
      sComp = new SmartComp(pairs.get(prev), dict, ledDistMap, keyDistMap);
    } else {
      sComp = new SmartComp(null, dict, ledDistMap, keyDistMap);
    }
    PriorityQueue<String> pq = new PriorityQueue<String>(sComp);

    for (String s : suggestions) {
      pq.add(s);
    }

    while (sorted.size() < NUM_SUG_RETURN) {
      String polled = pq.poll();
      if (polled != null) {
        sorted.add(pq.poll());
      } else {
        return sorted;
      }
    }

    return sorted;
  }

  /** Calculates the distance from one word to another on a QWERTY keyboard.
   * @param a A string
   * @param b A string
   * @return The calculated distance between the words based on keyboard
   *         distance */
  public double keyDist(String a, String b) {
    double dist = 0;
    int length;

    if (a.length() < b.length()) {
      length = a.length();
    } else {
      length = b.length();
    }

    for (int i = 0; i < length; i++) {
      dist += keys.dist(a.charAt(i), b.charAt(i));
    }

    return dist;
  }

  /** Standard rank function. Ranking firstly by exact match, then bigram
   * probability, then unigram probability, then alphabetically.
   * @param suggestions A list of Strings
   * @param original The original word to be Autocorrected
   * @param prev The previous word from the input or null if none exists
   * @return returns a list of sorted words */
  public List<String> standardRank(List<String> suggestions, String original,
      String prev) {
    List<String> sorted = new ArrayList<String>();
    HashMap<String, Trie.Count> biMap = pairs.get(prev);
    if (biMap != null) {
      biComp = new Comp(biMap);
    }

    // Exact match
    if (suggestions.contains(original)) {
      suggestions.remove(original);
      sorted.add(original);
    }

    // Ranking by bigram, then unigram, then alphabetically
    if (prev != null && biMap != null) {
      PriorityQueue<String> pq = new PriorityQueue<String>(biComp);
      List<String> remove = new ArrayList<String>();

      for (String s : suggestions) {
        String[] split = s.split(" ");
        String s0 = s;

        if (split.length > 1) {
          s0 = split[0];
        }

        if (biMap.containsKey(s0)) {
          remove.add(s);
          pq.add(s);
        }
      }

      for (String s : remove) {
        suggestions.remove(s);
      }

      while (!pq.isEmpty()) {
        List<String> equal = new ArrayList<String>();
        String curr = pq.poll();

        if (!pq.isEmpty()) {
          if (biComp.compare(curr, pq.peek()) != 0) {
            sorted.add(curr);
          } else {
            equal.add(curr);
            while (biComp.compare(pq.peek(), curr) == 0) {
              curr = pq.poll();
              equal.add(curr);
              if (pq.isEmpty()) {
                break;
              }
            }
            List<String> uniRanked = unigramRank(equal);
            for (String s : uniRanked) {
              sorted.add(s);
              if (sorted.size() == NUM_SUG_RETURN) {
                return sorted;
              }
            }
          }
        } else {
          sorted.add(curr);
        }

        if (sorted.size() == NUM_SUG_RETURN) {
          return sorted;
        }
      }
    }

    List<String> uniRanked = unigramRank(suggestions);
    for (String s : uniRanked) {
      sorted.add(s);
      if (sorted.size() == NUM_SUG_RETURN) {
        return sorted;
      }
    }

    return sorted;
  }

  /** Unigram rank function. Takes in a list of Strings and sorts firstly based
   * on unigram probability and then secondarily in alphabetical order.
   * @param words A list of words to sort
   * @return A new list of sorted words */
  public List<String> unigramRank(List<String> words) {
    List<String> sorted = new ArrayList<String>();

    PriorityQueue<String> pq = new PriorityQueue<String>(new Comp(dict));
    for (String w : words) {
      pq.add(w);
    }

    while (!pq.isEmpty()) {
      List<String> equal = new ArrayList<String>();
      String curr = pq.poll();

      if (!pq.isEmpty()) {
        if (uniComp.compare(curr, pq.peek()) != 0) {
          sorted.add(curr);
        } else {
          equal.add(curr);
          while (uniComp.compare(pq.peek(), curr) == 0) {
            curr = pq.poll();
            equal.add(curr);
            if (pq.isEmpty()) {
              break;
            }
          }
          Collections.sort(equal);
          for (String w : equal) {
            sorted.add(w);
          }
        }
      } else {
        sorted.add(curr);
      }
    }
    return sorted;
  }

  /** Comparator for smart rank.
   * @author tderosa */
  private static class SmartComp implements Comparator<String> {
    private HashMap<String, Trie.Count> biMap, uniMap;
    private HashMap<String, Integer> ledMap, keyMap;

    public SmartComp(HashMap<String, Trie.Count> bi,
        HashMap<String, Trie.Count> uni, HashMap<String, Integer> led,
        HashMap<String, Integer> key) {
      biMap = bi;
      uniMap = uni;
      ledMap = led;
      keyMap = key;
    }

    @Override
    public int compare(String a, String b) {
      String a0 = a;
      String b0 = b;
      String[] aS = a.split(" ");
      String[] bS = b.split(" ");

      if (aS.length > 1) {
        a0 = aS[0];
      }
      if (bS.length > 1) {
        b0 = bS[0];
      }

      double val1 = 0;
      double val2 = 0;

      val1 -= LED_WEIGHT * ledMap.get(a);
      val2 -= LED_WEIGHT * ledMap.get(b);

      val1 -= KEY_WEIGHT * keyMap.get(a);
      val2 -= KEY_WEIGHT * keyMap.get(b);

      if (biMap != null) {
        if (biMap.get(a0) != null) {
          val1 += BIGRAM_WEIGHT * biMap.get(a0).getInt();
        }
        if (biMap.get(b0) != null) {
          val2 += BIGRAM_WEIGHT * biMap.get(b0).getInt();
        }
      }

      val1 += 1 / uniMap.get(a0).getInt();
      val2 += 1 / uniMap.get(b0).getInt();

      if (val1 < val2) {
        return 1;
      } else if (val1 > val2) {
        return -1;
      } else {
        return 0;
      }
    }
  }

  /** Bigram and unigram comparator.
   * @author tderosa */
  private static class Comp implements Comparator<String> {
    private HashMap<String, Trie.Count> map;

    public Comp(HashMap<String, Trie.Count> m) {
      map = m;
    }

    @Override
    public int compare(String a, String b) {
      String a0 = a;
      String b0 = b;
      String[] aS = a.split(" ");
      String[] bS = b.split(" ");

      if (aS.length > 1) {
        a0 = aS[0];
      }
      if (bS.length > 1) {
        b0 = bS[0];
      }

      int val1 = 0;
      int val2 = 0;

      if (map.get(a0) != null) {
        val1 = map.get(a0).getInt();
      }
      if (map.get(b0) != null) {
        val2 = map.get(b0).getInt();
      }

      if (val1 < val2) {
        return 1;
      } else if (val1 > val2) {
        return -1;
      } else {
        return 0;
      }
    }
  }

  /** Private inner class representing keyboard locations on a QWERTY keyboard.
   * @author tderosa */
  private static class Keyboard {
    private HashMap<Character, Integer> row;
    private HashMap<Character, Integer> col;

    public Keyboard() {
      row = new HashMap<Character, Integer>();
      col = new HashMap<Character, Integer>();

      row.put('q', 0);
      col.put('q', 0);
      row.put('w', 0);
      col.put('w', 1);
      row.put('e', 0);
      col.put('e', 2);
      row.put('r', 0);
      col.put('r', 3);
      row.put('t', 0);
      col.put('t', 4);
      row.put('y', 0);
      col.put('y', 5);
      row.put('u', 0);
      col.put('u', 6);
      row.put('i', 0);
      col.put('i', 7);
      row.put('o', 0);
      col.put('o', 8);
      row.put('p', 0);
      col.put('p', 9);

      row.put('a', 1);
      col.put('a', 0);
      row.put('s', 1);
      col.put('s', 1);
      row.put('d', 1);
      col.put('d', 2);
      row.put('f', 1);
      col.put('f', 3);
      row.put('g', 1);
      col.put('g', 4);
      row.put('h', 1);
      col.put('h', 5);
      row.put('j', 1);
      col.put('j', 6);
      row.put('k', 1);
      col.put('k', 7);
      row.put('l', 1);
      col.put('l', 8);

      row.put('z', 2);
      col.put('z', 0);
      row.put('x', 2);
      col.put('x', 1);
      row.put('c', 2);
      col.put('c', 2);
      row.put('v', 2);
      col.put('v', 3);
      row.put('b', 2);
      col.put('b', 4);
      row.put('n', 2);
      col.put('n', 5);
      row.put('m', 2);
      col.put('m', 6);

      row.put(' ', 3);
      col.put(' ', 4);
    }

    /** This method returns the keyboard distance from a to b.
     * @param a - a key
     * @param b - a key
     * @return - the keyboard distance from a to b */
    public double dist(char a, char b) {
      double dist = Math.pow(row.get(a) - row.get(b), 2)
          + Math.pow(col.get(a) - col.get(b), 2);

      return dist;
    }

  }
}
