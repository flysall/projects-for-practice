
package flysall.tetris.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

import javax.imageio.ImageIO;

import flysall.tetris.exception.GameException;
import flysall.tetris.object.*;

public class ImageUtil {
	public static BufferedImage getImage(String imagePath) {
		try {
			// ImageIO读取图片
			return ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			throw new GameException("read image error");
		}
	}

	/**
	 * 在界面上画一个Piece对象
	 * @param g
	 * @param piece
	 */
	public static void paintPiece(Graphics g, Piece piece) {
		if (piece == null)
			return;
		for (int i = 0; i < piece.getSquares().size(); i++) {
			Square s = piece.getSquares().get(i);
			g.drawImage(s.getImage(), s.getBeginX(), s.getBeginY(), null);
		}
	}
}
