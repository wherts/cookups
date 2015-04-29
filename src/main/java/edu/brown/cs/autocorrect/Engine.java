package edu.brown.cs.autocorrect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Engine {
  private Trie trie;

  public Engine(Trie trie) {
    this.trie = trie;
  }

  public List<String> generate(String word) {
    List<String> suggestions = new ArrayList<String>();
    prefixMatch(suggestions, word);
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
}