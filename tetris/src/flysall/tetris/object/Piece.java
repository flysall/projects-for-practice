package flysall.tetris.object;

import java.util.*;

/**
 * 一个块对象，一个块包含多个方块
 */
public class Piece {
	// 该大方块所包含的小方块
	private List<Square> squares;
	// 大方块的变化
	protected List<List<Square>> changes = new ArrayList<List<Square>>();

	protected Random random = new Random();
	// 当前变化的索引
	protected int currentIndex;
	// 每个小块的边长
	public final static int SQUARE_BORDER = 16;

	public List<Square> getSquares() {
		return squares;
	}

	public void setSquares(List<Square> squares) {
		this.squares = squares;
	}

	public List<Square> getDefault() {
		// 从changes集合中随机得到一种变体
		int defaultChange = random.nextInt(changes.size());
		// 设置为当前变化的索引
		this.currentIndex = defaultChange;
		return changes.get(defaultChange);
	}

	public void change() {
		if (this.changes.size() == 0)
			return;
		this.currentIndex += 1;
		// 若当前变化超过changes集合的大小，重置为0
		if (this.currentIndex >= this.changes.size())
			this.currentIndex = 0;
		this.squares = this.changes.get(this.currentIndex);
	}

	// 让Piece对象中Square对象增加x参数
	public void setSquaresXLocation(int x) {
		for (int i = 0; i < this.changes.size(); i++) {
			List<Square> change = this.changes.get(i);
			for (int j = 0; j < change.size(); j++) {
				Square s = change.get(j);
				s.setBeginX(s.getBeginX() + x);
			}
		}
	}

	// 让Piece对象中Square对象增加y参数
	public void setSquaresYLocation(int y) {
		for (int i = 0; i < this.changes.size(); i++) {
			List<Square> change = this.changes.get(i);
			for (int j = 0; j < change.size(); j++) {
				Square s = change.get(j);
				s.setBeginY(s.getBeginY() + y);
			}
		}
	}

	// 获取当前变化中x坐标的最大值
	public int getMaxXLocation() {
		int result = 0;
		for (int i = 0; i < this.squares.size(); i++) {
			Square s = this.squares.get(i);
			if (s.getBeginX() > result)
				result = s.getBeginX();
		}
		return result + SQUARE_BORDER;
	}

	// 获取当前变化中x坐标的最小值
	public int getMinXLocation() {
		int result = 0;
		for (int i = 0; i < this.squares.size(); i++) {
			Square s = this.squares.get(i);
			if (s.getBeginX() < result)
				result = s.getBeginX();
		}
		return result;
	}

	// 获取当前变化中y坐标的最大值
	public int getMaxYLocation() {
		int result = 0;
		for (int i = 0; i < this.squares.size(); i++) {
			Square s = this.squares.get(i);
			if (result < s.getBeginY())
				result = s.getBeginY();
		}
		return result + SQUARE_BORDER;
	}

	// 获取当前变化中y坐标的最小值
	public int getMinYLocation() {
		int result = 0;
		for (int i = 0; i < this.squares.size(); i++) {
			Square s = this.squares.get(i);
			if (result > s.getBeginY())
				result = s.getBeginY();
		}
		// ?
		return result + SQUARE_BORDER;
	}

}
