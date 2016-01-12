package Hangman;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class TextViewer implements Viewer {
	private WordDictionary dictionary;
	private PrintStream out;
	private Scanner in;
	private String wordToGuess;
	private ArrayList<String> possibilities;
	private String[] guessed;
	private int errors;
	final private int maxErrors = 10;

	public TextViewer(WordDictionary dict, Scanner s, PrintStream outStream) {
		dictionary = dict;
		out = outStream;
		in = s;
	}

	@Override
	public void startGame() {
		out.println("Welcome to the text version of Hangman./n");
		int res = askIntQuestion("Please specifiy if you want a random word to guess (1) or specify a word (2):", 0, new Integer[]{1,2});
		if (res == 1) {
			int maxLength = askIntQuestion("Please specifiy the maximum length of the word you want to guess:", 0, new Integer[]{});
			wordToGuess = dictionary.getRandomWord(maxLength);
		} else {
			wordToGuess = askQuestion("Please enter the user defined word:", new String[]{});
		}
		out.println(wordToGuess);
		play();
	}

	private void play() {
		errors = 0;

		guessed = new String[wordToGuess.length()];
		Arrays.fill(guessed, "_");

		possibilities = new ArrayList<String>();
		possibilities.addAll(Arrays.asList("a b c d e f g h i j k l m n o p q r s t u v w x y z".split(" ")));

		String answer;
		while (contains(guessed, "_")) {
			out.println("The guesses until now are: " + String.join(" ", guessed));
			out.println("Mistakes made: " + errors + " / " + maxErrors);
			out.println("The possibilities are: " + possibilities.toString().substring(1, possibilities.size() * 3 - 1));
			answer = askQuestion("Please enter a letter:", possibilities.toArray(new String[]{}));
			letterPlayed(answer);
		}
	}

	@Override
	public void letterPlayed(String c) {
		possibilities.remove(c);

		if (!testLetter(c)) {
			errors++;
			out.println("That letter is not present in the word.");
			if (errors == maxErrors) {
				gameEnded(0);
			}
		} else {
			out.println("That letter is present in the word.");
			if (!contains(guessed, "_")){
				gameEnded(1);
			}
		}

	}

	public boolean testLetter(String letter) {
		String searchString = wordToGuess;
		while (searchString.indexOf(letter) != -1) {
			guessed[wordToGuess.length() - searchString.length() + searchString.indexOf(letter)] = letter;
			searchString = searchString.substring(searchString.indexOf(letter) + 1);
		}
		return wordToGuess.contains(letter);
	}

	@Override
	public void gameEnded(int result) {
		String message;
		if (result == 1) {
			message = "You have won the game. Congratulations!\nDo you want to play another game (1) or not (0)?";
		} else {
			message = "You have lost the game. The word was '" + wordToGuess + "'.\nDo you want to play another game (1) or not (0)?";
		}
		int choice = askIntQuestion(message, -1, new Integer[]{0,1});
		if (choice == 1) {
			startGame();
		} else {
			out.println("Thank you for playing. Hope you come again.");
			System.exit(1);
		}

	}

	private int askIntQuestion(String question, int biggerThan, Integer[] possibleAnswers) {
		Integer res = null;
		while (res == null) {
			out.println(question);
			try {
				res = Integer.parseInt(in.nextLine());
				//if possibleAnswers contains nothing, all results are correct
				if (!contains(possibleAnswers, res) && possibleAnswers.length > 0 || res < biggerThan) {
					out.println("This is not a possible answer.");
					res = null;
				}
			} catch (NumberFormatException e) {
				out.println("This is not a possible answer.");
				res = null;
			}
		}
		return res;
	}

	private String askQuestion(String question, String[] possibleAnswers) {
		String res = null;
		while (res == null) {
			out.println(question);
			res = in.nextLine();
			//if possibleAnswers contains nothing, all results are correct
			if (!contains(possibleAnswers, res) && possibleAnswers.length > 0) {
				out.println("This is not a possible answer.");
				res = null;
			}
		}
		return res;
	}

	private <T> boolean contains(T[] array, T item) {
		for (T arrItem : array) {
			if (arrItem.equals(item)) {
				return true;
			}
		}
		return false;
	}
}
