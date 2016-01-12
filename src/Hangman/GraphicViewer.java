package Hangman;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class GraphicViewer extends JFrame implements Viewer{
	LetterPanel letterPanel;
	GamePanel gamePanel;
	WordPanel wordPanel;
	DrawingPanel drawingPanel;
	WordDictionary dict;
	int errorCount;

	public GraphicViewer(WordDictionary d) {
		super("Hangman");
		setSize(640, 600);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));



		gamePanel = new GamePanel();
		gamePanel.setPreferredSize(new Dimension(this.getSize().width, this.getSize().height - 50));
//		addBorder(gamePanel, "Game Panel");

		wordPanel = new WordPanel();
//		addBorder(wordPanel, "Word panel");
		gamePanel.add(wordPanel, BorderLayout.PAGE_START);

		drawingPanel = new DrawingPanel(this);
		drawingPanel.setPreferredSize(new Dimension(gamePanel.getSize().width, gamePanel.getSize().height - 50));
//		addBorder(drawingPanel, "Drawing panel");
		
		gamePanel.add(drawingPanel, BorderLayout.CENTER);




		this.add(gamePanel);
		letterPanel = new LetterPanel(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton source = (JButton) e.getSource();
				source.setEnabled(false);
				letterPlayed(source.getText().substring(0, 1));
			}
		});
		letterPanel.setPreferredSize(new Dimension(gamePanel.getSize().width, 200));
		addBorder(letterPanel, "Letter Panel");
		this.add(letterPanel);

		dict = d;
		this.setVisible(true);
	}

	private void addBorder(JComponent component, String title) {
		Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		if (!title.equals("")){
			etch = BorderFactory.createTitledBorder(etch,title);
		}
		component.setBorder(etch);
	}

	public void letterPlayed(String s) {
		if (!wordPanel.testLetter(s)) {
			errorCount++;
			drawingPanel.drawHangman(errorCount);
			if (errorCount == 10) {
				gameEnded(0);
			}
		} else if (wordPanel.getWord().length() == wordPanel.getLettersGuessed()) {
			gameEnded(1);
		}
	};

	public void startGame() {
		errorCount = 0;
		int n = JOptionPane.showOptionDialog(this,
				"Do you want to get a random word or do you want to input one?",
				"Random word?",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				new String[]{"Random word", "User input"},
				"Random word");

		String chosen = null;
		if (n == 0) {
			int maxLength = 0;
			while (maxLength < 1) {
				try {
					maxLength = Integer.parseInt((String) JOptionPane.showInputDialog(
							this,
							"Please choose the maximum length of the word.",
							"Maximum Length",
							JOptionPane.PLAIN_MESSAGE,
							null,
							null,
							""));
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(this,
							"Please pick an option.");
					startGame();
				}
				
			}
			chosen = dict.getRandomWord(maxLength);
		} else {
			do {
				chosen = (String) JOptionPane.showInputDialog(
						this,
						"Please choose a word by typing it into the textbox below and the click OK\nOnly letters of the alphabet are allowed.",
						"Choose a word",
						JOptionPane.PLAIN_MESSAGE,
						null,
						null,
						"");
			} while (!chosen.chars().allMatch(x -> Character.isLetter(x)) || chosen.length() < 1);
			
		}
		if (chosen == null || chosen.equals("")) {
			JOptionPane.showMessageDialog(this,
					"Please pick an option.");
			startGame();
		} else { 
			letterPanel.enableAll(true);
			wordPanel.setWord(chosen);
		}
	}

	public void gameEnded(int result) {
		String message;
		String title;
		if (result == 1) {
			message = "You have won the game. Congratulations!\nDo you want to play another game?";
			title = "Game won!";
		} else {
			message = "You have lost the game. The word was '" + wordPanel.getWord() + "'.\nDo you want to play another game?";
			title = "Game lost!";
		}
		int choice = JOptionPane.showOptionDialog(this,
				message,
				title,
				JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				null,
				new String[]{"Yes", "No"},
				"Yes");
		drawingPanel.resetGraphics();
		if (choice == 0) {
			letterPanel.enableAll(true);
			startGame();
		} else {
			this.setVisible(false);
			this.dispose();
		}
	}
}
