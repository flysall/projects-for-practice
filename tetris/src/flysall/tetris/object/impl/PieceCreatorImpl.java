package flysall.tetris.object.impl;

import java.awt.*;
import java.util.*;

import flysall.tetris.object.*;
import flysall.tetris.piece.*;
import flysall.tetris.util.ImageUtil;

public class PieceCreatorImpl implements PieceCreator{
	//缓存每个Square
	private Map<Integer, Image> images = new HashMap<Integer, Image>();
	
	//共计七种颜色方案
	private final static int COLOR_SIZE = 7;
	
	//共计七种大方块
	private final static int SQUARE_SIZE = 7;
	private Random random = new Random();
	
	@Override 
	public Piece createPiece(int x, int y) {
		Image image = getImage(random.nextInt(COLOR_SIZE));
		Piece piece = initPiece(image);
		piece.setSquaresXLocation(x);
		piece.setSquaresYLocation(y);
		return piece;
	}
	
	private Piece initPiece(Image image) {
		Piece piece = null;
		int pieceType = random.nextInt(SQUARE_SIZE); 
		//初始化 Piece，随机创建各个大方块
		if (pieceType == 0){
			piece = new Piece0(image);
		} else if (pieceType == 1) {
			piece = new Piece1(image);
		} else if (pieceType == 2) {
			piece = new Piece2(image);
		} else if (pieceType == 3) {
			piece = new Piece3(image);
		} else if (pieceType == 4) {
			piece = new Piece4(image);
		} else if (pieceType == 5) {
			piece = new Piece5(image);
		} else if (pieceType == 6) {
			piece = new Piece6(image);
		}
		return piece;
	}
	
	/**
	 * 从map中得到图片对象，如果map中没有存在图片对象, 则创建
	 * @param key
	 * @return
	 */
	private Image getImage(int key) {
		if (this.images.get(key) == null) {
			Image s = ImageUtil.getImage("images/square" + key + ".jpg");
			this.images.put(key, s);
		}
		return this.images.get(key);
	}
	
	@Override 
	public Piece getPiece() {
		return null;
	}
}


















