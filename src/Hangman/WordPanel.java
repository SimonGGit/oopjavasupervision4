package Hangman;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class WordPanel extends JPanel{
	
	private JLabel[] labels;
	private String word;
	private int lettersGuessed;
	
	public WordPanel() {
	}
	
	public String getWord() {
		return word;
	}
	public int getLettersGuessed() {
		return lettersGuessed;
	}
	
	public void setWord(String s) {
		this.removeAll();
		this.repaint();
		lettersGuessed = 0;
		word = s;
		labels = new JLabel[word.length()];
		int count = 0;
		for (char c : word.toCharArray()) {
			labels[count] = new JLabel(String.valueOf(c));
			labels[count].setText("_");
			labels[count].setFont(new Font("Times New Roman", Font.PLAIN, 20));
			this.add(labels[count]);
			count++;
		}
		//necessary to get the layout correct;
		labels[0].setText("a");
		labels[0].setText("_");
		this.validate();
	}
	
	public boolean testLetter(String letter) {
		// TODO
		String searchString = word;
		while (searchString.indexOf(letter) != -1) {
			lettersGuessed++;
			labels[word.length() - searchString.length() + searchString.indexOf(letter)].setText(letter);
			searchString = searchString.substring(searchString.indexOf(letter) + 1);
		}
		return word.contains(letter);
	}
	
}
