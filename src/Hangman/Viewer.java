package Hangman;

public interface Viewer {
	
	void startGame();
	void letterPlayed(String c);
	void gameEnded(int result);
	
}
