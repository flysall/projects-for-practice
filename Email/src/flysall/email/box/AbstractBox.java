package flysall.email.box;

import javax.swing.ImageIcon;

/**
 * 导航树的节点抽象类
 */
public abstract class AbstractBox implements MailBox {
	// 该box对应的图片
	private ImageIcon icon;

	// 实现接口方法
	public ImageIcon getImageIcon(String imagePath) {
		if (this.icon == null) {
			this.icon = new ImageIcon(imagePath);
		}
		return this.icon;
	}

	// 重写toString方法, 调用接口的getText方法, getText方法由子类去实现
	public String toString() {
			return getText();
	}
}
