package flysall.tetris.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;

import javax.swing.JPanel;

import flysall.tetris.object.*;
import flysall.tetris.object.impl.*;
import flysall.tetris.util.ImageUtil;

public class GamePanel extends JPanel{
	MainFrame mainFrame;
	
	private Image background = ImageUtil.getImage("images/background.jpg");
	
	public GamePanel (MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void paint(Graphics g) {
		g.drawImage(this.background, 0, 0, this.getWidth(),
				this.getHeight(), null);
		Piece currentPiece = this.mainFrame.getCurrentPiece();
		ImageUtil.paintPiece(g, currentPiece);
		Square[][] squares = this.mainFrame.getSquares();
		if (squares == null) 
			return;
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0 ; j < squares[i].length; j++) {
				Square s = squares[i][j];
				if (s != null) {
					g.drawImage(s.getImage(),s.getBeginX(), s.getBeginY(),this);
				}
			}
		}
	}
}





















