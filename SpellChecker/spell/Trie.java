package spell;
/**
 * Search tree storing items as sequences of characters
 */
 public class Trie {

   public Node root;
   public int nodeCount;
   public int wordCount;

   public Trie() {
       root = new Node();
       nodeCount = 1;
       wordCount = 0;
   }

   /**
 	 * Recursively adds a word to the trie by adding one character
   * from the input at a time from the root node
   * Ex: "word" -> root["w"].add("ord").add("rd").add("d")
   * @param word Word added to the Trie
 	 */
   public void add(String word) {
       // c = first letter in word
       char c = word.charAt(0);

       if (root.nodes[c - 'a'] == null) // Create root[c] if it hasn't already
          root.nodes[c - 'a'] = new Node();

       root.nodes[c - 'a'].add(word.substring(1), word);
   }

   /**
 	 * Recursively finds a node in the Trie by incrementing through characters in the input string
   * ex: "word" -> root["w"].find("ord").find("rd").find("d")
   * @param word The word searched for in the Trie
   * @return The node for the last character of the input string in the trie
 	 */
   public Node find(String word) {
       // c = first letter in word
       char c = word.charAt(0);

       // Return null if root[c] hasn't been created
       if (root.nodes[c - 'a'] == null)
          return null;
       else
          return root.nodes[c - 'a'].find(word.substring(1));
   }

   // Gets the number of occurences of a word in the trie
   public int getWordCount() { return wordCount; }

   // Gets the number of total nodes in the trie
   public int getNodeCount() { return nodeCount; }

   /**
 	 *
 	 */
   public class Node {

     // The number of times this node had occurred in the trie
     private int value = 0;
     // The characters of the nodes prior to this node
     private String path;
     // The the nodes within the current node
     public Node[] nodes;

     public Node() {
       nodes = new Node[26];
       nodeCount++;
     }

     /**
   	 * Recursively adds characters in the word to the node
     * Ex: add("ords", "words") -> node.add("ords", "words").add("rds", "words").add("ds", "words").add("s", "words")
     * @param cur The current substring of the original word
     * @param word Word added to the Trie
   	 */
     public void add(String cur, String word) {
       // If this is the last character in the word
       if (cur.equals("")) {
         if (value == 0) {
           wordCount++;
           path = word;
         }

         value++;
         return;
       }

       char c = cur.charAt(0);

       if (nodes[c - 'a'] == null)
          nodes[c - 'a'] = new Node();
       nodes[c - 'a'].add(cur.substring(1), word);
     }

     /**
   	 * Recursively finds the node for the last character in a word
     * Ex:find("word") = node.find("word").find("ord").find("rd").find("d")
     * @param cur The current substring of the original word
     * @param word Word added to the Trie
   	 */
     public Node find(String word) {
       // Return this node if it's the last character in the word
       if (word.equals("")) {
         if (value == 0) return null;
         else return this;
       }

       char c = word.charAt(0);

       if (nodes[c - 'a'] == null) return null;
       else return nodes[c - 'a'].find(word.substring(1));
     }

     // Gets the number of times this node occurred in the trie
     public int getValue() { return value; }

     // Gets the characters for the nodes prior to this one
     public String getPath() { return path; }
   }

 }
