package flysall.tetris.object;

import java.awt.Image;

/**
 * 一个方块对象
 */
public class Square {
	// 方块的图片
	private Image image;
	// 开始的横坐标
	private int beginX;
	// 开始的纵坐标
	private int beginY;

	public Square(Image image, int beginX, int bebinY) {
		this.image = image;
		this.beginX = beginX;
		this.beginY = beginY;
	}

	public Square(int beginX, int beginY) {
		this.beginX = beginX;
		this.beginY = beginY;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getBeginX() {
		return beginX;
	}

	public void setBeginX(int beginX) {
		this.beginX = beginX;
	}

	public int getBeginY() {
		return beginY;
	}

	public void setBeginY(int beginY) {
		this.beginY = beginY;
	}

	public boolean equalse(Object obj) {
		if (obj instanceof Square) {
			Square s = (Square) obj;
			if ((s.getBeginX() == this.getBeginX() && (s.getBeginY() == this.getBeginY()))) {
				return true;
			}
		}
		return false;
	}
}
