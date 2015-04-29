package edu.brown.cs.autocorrect;

import java.util.HashMap;

/** This is the TrieNode class. Each TrieNode holds a character. Nodes that
 * represent the end of the word are designated by a boolean and additionally
 * have a String containing the word.
 * @author tderosa */
public class TrieNode {
  private HashMap<Character, TrieNode> children;
  private boolean end;
  private String word;
  private char letter;

  /** Constructor for TrieNode.
   * @param l the character to be stored at the node
   * @param e boolean representing the end of the word */
  public TrieNode(char l, boolean e) {
    end = e;
    letter = l;
    children = new HashMap<Character, TrieNode>();
    word = "";
  }

  /** Checks if this node represents the end of the word.
   * @return returns true if the node is at the end of the word */
  public boolean end() {
    return end;
  }

  /** Designates the end of the word and sets boolean to true. */
  public void endTrue() {
    end = true;
  }

  /** Sets the word at the current Node.
   * @param w the word to be stored */
  public void setWord(String w) {
    word = w;
  }

  /** Gets the word stored at the node if it is the node at the end of the word.
   * @return returns the word as a String */
  public String word() {
    return word;
  }

  /** Gets the character at the node.
   * @return returns a char */
  public char letter() {
    return letter;
  }

  /** Gets all children of the TrieNode.
   * @return returns a HashMap of child nodes */
  public HashMap<Character, TrieNode> children() {
    return children;
  }

  /** Gets the child node at the given character.
   * @param c character of child node
   * @return returns the child node or null if none exists */
  public TrieNode getChild(char c) {
    return children.get(c);
  }

  /** Adds a child node to the HashMap of child nodes.
   * @param c character of child
   * @param child TrieNode representing child
   * @return returns the child TrieNode added to HashMap */
  public TrieNode addChild(char c, TrieNode child) {
    return children.put(c, child);
  }
}
