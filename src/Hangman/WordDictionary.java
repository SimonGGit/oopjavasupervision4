package Hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WordDictionary {
	String[] dict;

	public WordDictionary(File f) {
		ArrayList<String> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f))) ){
			String line = reader.readLine();
			while (line != null) {
				list.add(line);
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		dict = list.toArray(new String[list.size()]);
	}

	public WordDictionary(String[] l) {
		dict = l;
	}

	public String getRandomWord(int maxLength) {
		while (true) {
			int rand = (int) (Math.random() * dict.length);
			if (dict[rand].length() <= maxLength) {
				return dict[rand];
			}
			
		}
	}
}
