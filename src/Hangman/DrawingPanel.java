package Hangman;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel{
	private final int margin = 20;
	private Graphics drawGraphics;
	
	public DrawingPanel(JFrame j) {
		drawGraphics = this.getGraphics();
	}
	
	public void drawHangman(int error) {
		if (drawGraphics == null) {
			drawGraphics = this.getGraphics();
		}
		if (error == 1) {
			drawGraphics.setColor(new Color(1, 1, 0));
			drawGraphics.drawLine(250, this.getHeight() - margin, 450, this.getHeight() - margin);
		} else if (error == 2) {
			drawGraphics.drawLine(250, this.getHeight() - margin, 250, margin);
		} else if (error == 3) {
			drawGraphics.drawLine(250, this.getHeight() - 50 - margin, 300, this.getHeight() - margin);
		} else if (error == 4) {
			drawGraphics.drawLine(250, margin, 350, margin);
		} else if (error == 5) {
			drawGraphics.drawLine(250, 50 + margin, 300, margin);
		} else if (error == 6) {
			drawGraphics.drawLine(350, margin, 350, margin + 50);
		} else if (error == 7) {
			drawGraphics.drawOval(325, 50 + margin, 50, 50);
		} else if (error == 8) {
			drawGraphics.drawOval(310, 100 + margin, 80, 130);
		} else if (error == 9) {
			drawGraphics.drawLine(385, 150, 415, 120);
			drawGraphics.drawLine(316, 150, 286, 120);
		} else if (error == 10) {
			drawGraphics.drawLine(375, 235, 400, 295);
			drawGraphics.drawLine(325, 235, 300, 295);
		}
	}
	
	public void resetGraphics() {
		this.repaint();
	}
}
