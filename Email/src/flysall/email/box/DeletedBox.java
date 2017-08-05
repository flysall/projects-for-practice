package flysall.email.box;

import javax.swing.ImageIcon;

/**
 * 垃圾箱
 */
public class DeletedBox extends AbstractBox {
	private ImageIcon icon;

	public String getText() {
		return "垃圾箱";
	}

	public ImageIcon getImageIcon() {
		return super.getImageIcon("images/deleted-tree.gif");
	}
}
