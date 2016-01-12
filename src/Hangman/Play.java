package Hangman;

import java.io.File;
import java.util.Scanner;

public class Play {

	public static void main(String[] args) {
		WordDictionary dict = new WordDictionary(new File(args[0]));
		Viewer gui = null;
		
		int implementation;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to use a Textual Interface (1) or a Graphical Interface(2, default)?");
		try {
			implementation = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			implementation = 2;
		}
		if (implementation > 2) { implementation = 2; }
		
		if (implementation == 1) {
			gui = new TextViewer(dict, scanner, System.out);
		} else if (implementation == 2) {
			gui = new GraphicViewer(dict);
		}

		gui.startGame();
	}

}
