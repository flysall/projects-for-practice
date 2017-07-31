package flysall.tetris.piece;

import java.awt.Image;
import java.util.*;

import flysall.tetris.object.*;

public class Piece0 extends Piece{
	public Piece0(Image image) {
		//创建各个方块，每个集合对应每种变化
		List<Square> squares = new ArrayList<Square>();
		squares.add(new Square(image, 0, 0));
		squares.add(new Square(image, 0, image.getHeight(null)));
		squares.add(new Square(image, image.getWidth(null), 0));
		squares.add(new Square(image, image.getWidth(null), image.getHeight(null)));
		//添加到变化中
		super.changes.add(squares);
		super.setSquares(squares);
	}
}
