package spell;

import java.io.IOException;

/**
 * A main class for running the spelling corrector.
 */
public class Main {

	/**
	 * The dictionary file is used as the first arguent and the word
	 * being spell corrected is the second argument
	 */
	public static void main(String[] args) throws IOException {

		String dictionaryFileName = args[0];
		String inputWord = args[1];

		SpellCorrector corrector = new SpellCorrector(dictionaryFileName);
		String suggestion = corrector.getSuggestedWord(inputWord);

		// If no suggested word found, inform the user
		if (suggestion == null) {
			System.out.println("No similar word found");
		}
		// Else, print suggested word
		else System.out.println("Suggestion is: " + suggestion);
	}
}
