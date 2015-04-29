package edu.brown.cs.autocorrect;

import java.util.HashMap;
import java.util.List;

/** This is the Trie class. Builds a Trie structure based upon a corpus of
 * Strings.
 * @author tderosa */
public class Trie {
  private TrieNode root;
  private HashMap<String, Count> unigram;
  private HashMap<String, HashMap<String, Count>> bigram;
  private int size = 0;

  /** Trie constructor.
   * @param dict a corpus of words to be represented in the Trie */
  public Trie(List<String> dict) {
    root = new TrieNode('\0', false);
    unigram = new HashMap<String, Count>();
    bigram = new HashMap<String, HashMap<String, Count>>();
    build(dict);
  }

  /** Inner class used as a mutable int. Able to increment and retrieve value.
   * @author tderosa */
  public static class Count {
    private int value = 1;

    /** Increments the value stored at count object. */
    public void increment() {
      value += 1;
    }

    /** Gets the value of count object.
     * @return returns an int that is the value of the count */
    public int getInt() {
      return value;
    }
  }

  /** Gets the root node.
   * @return returns a TrieNode representing the root of the Trie */
  public TrieNode root() {
    return root;
  }

  /** Gets a HashMap of all words in corpora.
   * @return returns a HashMap of all words in Trie to their frequency */
  public HashMap<String, Count> dict() {
    return unigram;
  }

  /** Gets all pairs of words from corpora Used to compute bigram probabilities.
   * @return returns a hashmap of previous words to succeeding words to
   *         frequency */
  public HashMap<String, HashMap<String, Count>> pairs() {
    return bigram;
  }

  /** Gets the number of elements in the Trie.
   * @return returns the size of the trie */
  public int size() {
    return size;
  }

  /** Iteratively builds the Trie using helper method.
   * @param words A list of words to be stored in the Trie */
  public void build(List<String> words) {
    String prev = null;

    for (String s : words) {
      Count c = unigram.get(s);
      if (c == null) {
        unigram.put(s, new Count());
        size++;
        add(s);
      } else {
        c.increment();
      }

      if (prev != null) {
        HashMap<String, Count> map = bigram.get(prev);
        if (map == null) {
          HashMap<String, Count> val = new HashMap<String, Count>();
          val.put(s, new Count());
          bigram.put(prev, val);
        } else {
          Count biC = map.get(s);
          if (biC == null) {
            map.put(s, new Count());
          } else {
            biC.increment();
          }
        }
      }
      prev = s;
    }
  }

  /** Iteratively adds a word to the Trie.
   * @param word The word to add */
  public void add(String word) {
    TrieNode curr = root;
    for (int i = 0; i < word.length(); i++) {
      TrieNode child = curr.getChild(word.charAt(i));
      if (child == null) {
        child = new TrieNode(word.charAt(i), false);
        curr.addChild(word.charAt(i), child);
      }
      if (i == word.length() - 1) {
        child.endTrue();
        child.setWord(word);
      }
      curr = child;
    }
  }

  /** Prints the Trie nodes based on depth, character, and word.
   * @param node The current node
   * @param depth The current depth */
  public void printTrie(TrieNode node, int depth) {
    System.out.println(String.valueOf(depth) + " // " + node.letter() + " // "
        + node.end() + " // " + node.word());
    for (TrieNode c : node.children().values()) {
      printTrie(c, depth + 1);
    }
  }

  /** Recursively finds all words stored in the Trie Used primarily for testing.
   * @param words A list of Strings to store the words in
   * @param node The current TrieNode
   * @param depth The current depth */
  public void getWords(List<String> words, TrieNode node, int depth) {
    for (TrieNode c : node.children().values()) {
      if (c.end()) {
        words.add(c.word());
      }
      getWords(words, c, depth + 1);
    }
  }
}
