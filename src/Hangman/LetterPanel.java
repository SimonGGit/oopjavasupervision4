package Hangman;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LetterPanel extends JPanel implements ActionListener {

	private final char[] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private JButton[] buttons;

	public LetterPanel(ActionListener actionListener) {
		super();
		setLayout(new GridLayout(3, 10));
		buttons = new JButton[26];

		int count = 0;
		for (char c : letters) {
			buttons[count] = new JButton(String.valueOf(c));
			buttons[count].addActionListener(actionListener);
			add(buttons[count]);		
			count++;
		}
		enableAll(false);
	}

	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		source.setEnabled(false);
	}

	public void enableAll(boolean b) {
		for (JButton button : buttons) {
			button.setEnabled(b);
		}
	}
}
