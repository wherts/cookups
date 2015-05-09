package edu.brown.cs.autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Engine {
  private Trie trie;
  public static final int MAX_LED_DIST = 2;

  public Engine(Trie trie) {
    this.trie = trie;
  }

  public List<String> generate(String word) {
    List<String> suggestions = new ArrayList<String>();
    prefixMatch(suggestions, word);
    ledMatch(suggestions, word);
    if (trie.dict().containsKey(word) && !suggestions.contains(word)) {
      suggestions.add(word);
    }
    return alphabetize(suggestions);
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

  /** Alphabetical Sort
   * @param words A list of words to sort
   * @return A new list of sorted words */
  public List<String> alphabetize(List<String> words) {
    Collections.sort(words);
    return words;
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
      int dist = levDist(node.word().toLowerCase(), word);
      if (dist <= MAX_LED_DIST) {
        if (!matches.contains(node.word())) {
          matches.add(node.word());
        }
      }
    }

    if (depth <= word.length() + MAX_LED_DIST) {
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
