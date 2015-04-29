package edu.brown.cs.autocorrect;

import java.util.List;

/** This is the suggestions generator. It has methods to generate suggestions
 * using a whitespace matcher, prefix matcher, and levenshtein edit distance
 * calculator.
 * @author tderosa */
public class Generator {
  private Trie trie;
  private int maxDist;

  /** Constructor for the suggestion generator.
   * @param t The trie where the corpus of words is held
   * @param maxD The maximum LED to use in calculations */
  public Generator(Trie t, int maxD) {
    trie = t;
    maxDist = maxD;
  }

  /** Finds all pairs of words that are subwords of the passed in word.
   * @param matches An arraylist of suggested words to modify
   * @param word The word to split */
  public void spaceMatch(List<String> matches, String word) {
    for (int i = 0; i < word.length(); i++) {
      String s1 = word.substring(0, i);
      String s2 = word.substring(i, word.length());
      if (trie.dict().containsKey(s1) && trie.dict().containsKey(s2)) {
        String newString = s1 + " " + s2;
        if (!matches.contains(newString)) {
          matches.add(newString);
        }
      }
    }

  }

  /** Finds all words within Trie that begin with a given prefix.
   * @param suggestions An arraylist of suggested words to modify
   * @param prefix The prefix to match to */
  public void prefixMatch(List<String> suggestions, String prefix) {
    TrieNode curr = trie.root();
    TrieNode child = null;

    // Finding prefix in tree
    for (int i = 0; i < prefix.length(); i++) {
      child = curr.getChild(prefix.charAt(i));
      if (child == null) {
        return;
      }
      curr = child;
    }

    // Call recursive method to find all possible words
    findWords(suggestions, curr);
  }

  /** Recursively finds all words that belong to child nodes of passed in node.
   * @param suggestions An arraylist of suggested words to modify
   * @param node The current node
   * @return returns a list of suggested words */
  public List<String> findWords(List<String> suggestions, TrieNode node) {
    if (node.end()) {
      if (!suggestions.contains(node.word())) {
        suggestions.add(node.word());
      }
    }

    for (TrieNode c : node.children().values()) {
      findWords(suggestions, c);
    }

    return suggestions;
  }

  /** Finds all words in Trie within a specified LED from the input word.
   * @param suggestions An arraylist of suggested words to modify
   * @param word The input word */
  public void ledMatch(List<String> suggestions, String word) {
    TrieNode curr = trie.root();

    recursiveLED(suggestions, curr, 0, word);
  }

  /** Recursively calculates the LED between a word and all words in the Trie.
   * @param matches An arraylist of suggested words to add to
   * @param node The current node
   * @param depth The current depth
   * @param word The word to match to */
  public void recursiveLED(List<String> matches, TrieNode node, int depth,
      String word) {

    if (node.end()) {
      int dist = levDist(node.word(), word);
      if (dist <= maxDist) {
        if (!matches.contains(node.word())) {
          matches.add(node.word());
        }
      }
    }

    if (depth <= word.length() + maxDist) {
      for (TrieNode c : node.children().values()) {
        recursiveLED(matches, c, depth + 1, word);
      }
    }
  }

  /** Calculates the levenshtein edit distance between two strings.
   * @param a string one
   * @param b string two
   * @return The LED between the two words */
  public int levDist(String a, String b) {
    int lenA = a.length() + 1;
    int lenB = b.length() + 1;

    int[] dist = new int[lenA];
    int[] newdist = new int[lenA];

    for (int i = 0; i < lenA; i++) {
      dist[i] = i;
    }

    for (int j = 1; j < lenB; j++) {
      newdist[0] = j;

      for (int i = 1; i < lenA; i++) {
        int same = 1;
        if (a.charAt(i - 1) == b.charAt(j - 1)) {
          same = 0;
        }

        int insert = dist[i] + 1;
        int delete = newdist[i - 1] + 1;
        int sub = dist[i - 1] + same;

        newdist[i] = Math.min(Math.min(insert, delete), sub);
      }

      int[] swap = dist;
      dist = newdist;
      newdist = swap;
    }

    return dist[lenA - 1];
  }
}
