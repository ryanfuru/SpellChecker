package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Class that implements the spell corrector
 */
public class SpellCorrector {

	// Contains all acceptable words
	public Trie dictionary;

	/**
	 * Constructor for the SpellCorrector class that creates the dictionary Object
	 * @param dictionaryFileName The file where the dictionary words are found
	 */
	 public SpellCorrector(String dictionaryFileName) throws IOException {
		 dictionary = new Trie();
		 Scanner s = new Scanner(new File(dictionaryFileName));

		 // Add each word from the dictionary file to the dictionary
		 while (s.hasNext()) {
		 	dictionary.add(s.next().toLowerCase());
		 }

		 s.close();
	 }

	/**
	 * Returns a word in the dictionary most similar to the input word
	 * @param inputWord word being spell checked
	 * @return word in dictionary closest to inputWord
	 */
	public String getSuggestedWord(String inputWord) {
		String word = inputWord.toLowerCase();

		// Return null if the input string is empty
		if (word.equals(""))
			return null;
		// Return the input string if it's found in the dictionary
		if (dictionary.find(word) != null)
			return word;

		// List of words to be searched for in the dictionary
		TreeSet<String> WordsToCheck = new TreeSet<String>();

		// Add words in the dictionary near the input string to WordsToCheck
		addSimilarWords(word, WordsToCheck);

		// The word in WordsToCheck appearing most in the dictionary
		String bestWord = getBestWord(WordsToCheck);

		// If a word in WordsToCheck was in the dictionary, return bestWord.
		if (bestWord != null) return bestWord;

		/*
		 * If a word wasn't found, add all the words in the dictionary
		 * similar to EACH word in WordToCheck to a new list, DoubleCheck
		 */
		TreeSet<String> DoubleCheck = new TreeSet<String>();

		for (String s: WordsToCheck) {
			addSimilarWords(s, DoubleCheck);
		}

		// bestWord = the word in DoubleCheck occuring most in the dictionary
		bestWord = getBestWord(DoubleCheck);

		// If a word in DoubleCheck was found in the dictionary, return the best word
		if (bestWord != null) return bestWord;
		// If a word wasn't found return null
		else return null;
  }

	/**
	 * Adds words in the dictionary to the inputted List
	 * IF they are the input word with one of the characters deleted
	 * ex: "cate" would add "cat", "cte", "cae", and "ate" to List
	 * @param word Input word used to find similar words in the dictionary
	 * @param List The list similar words are added to
	 */
	private void addDeletions(String word, TreeSet<String> List) {
		for (int i = 0; i < word.length(); i++) {
			StringBuilder s = new StringBuilder(word);

			s.deleteCharAt(i);
			List.add(s.toString());
		}
	}

	/**
	 * Adds words in the dictionary to the inputted list
	 * IF they are the input word where any two adjacent character are transposed
	 * ex: "cate" would add "acte", "ctae", and "caet" to List
	 * @param word Input word used to find similar words in the dictionary
	 * @param List The list similar words are added to
	 */
	private void addTranspositions(String word, TreeSet<String> List) {
		for (int i = 0; i < word.length() - 1; i++) {
			char[] c = word.toCharArray();
			char temp = c[i];

			c[i] = c[i + 1];
			c[i + 1] = temp;
			List.add(new String(c));
		}
	}

	/**
	 * Adds words in the dictionary to the inputted list
	 * IF they are the input word where any character is changed to another character
	 * ex: "dog" would add "aog", "bog", "cog", etc. to List
	 * @param word Input word used to find similar words in the dictionary
	 * @param List The list similar words are added to
	 */
	private void addAlterations(String word, TreeSet<String> List) {
		for (int i = 0; i < word.length(); i++) {
			for (int j = 0; j < 26; j++) {
				StringBuilder s = new StringBuilder(word);
				String c = Character.toString((char)(j + 'a'));

				s.replace(i, i + 1, c);
				List.add(s.toString());
			}
		}
	}

	/**
	 * Adds words in the dictionary to the inputted list
	 * IF they are the input word where a character is added to any point in the word
	 * ex: "cat" would add "acat", "bcat", "ccat", etc. to List
	 * @param word Input word used to find similar words in the dictionary
	 * @param List The list similar words are added to
	 */
	private void addInsertions(String word, TreeSet<String> List) {
		for (int i = 0; i < word.length() + 1; i++) {
			for (int j = 0; j < 26; j++) {
				StringBuilder s = new StringBuilder(word);

				s.insert(i, (char)(j + 'a'));
				List.add(s.toString());
			}
		}
	}

	/**
	 * Adds words similar to the input string to the input list
	 * @param s Input word used to find similar words in the dictionary
	 * @param List The list similar words are added to
	 */
	 private void addSimilarWords(String s, TreeSet<String> List) {
		 if (s.length() > 1) // Word length must be > 1 for there to be deletions
 			addDeletions(s, List);
 		addTranspositions(s, List);
 		addAlterations(s, List);
 		addInsertions(s, List);
	 }

	 /**
 	 * Find the word in a list occuring most in the dictionary
	 * @param List The list of words searched for in the dictionary
	 * @return The word in List found the most in the dictionary
 	 */
 	 private String getBestWord(TreeSet<String> List) {
		 String bestWord = null; // The word in list appearing most in the dictionary
 		 int bestFreq = 0; // The frequency of bestWord

		 for (String s: List) {
	 	 		// If s is in the dictionary
	 			if (dictionary.find(s) != null) {
	 				/*
	 				* If s is found in the dictionary more than bestWord,
	 				* update bestWord and bestFreq with s and the frequency of s
	 				*/
	 				if (dictionary.find(s).getValue() > bestFreq) {
	 					bestWord = dictionary.find(s).getPath();
	 					bestFreq = dictionary.find(s).getValue();
	 				}
	 			}
			}

			return bestWord;
 	 }

}
