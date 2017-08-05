package flysall.email.box;

import javax.swing.ImageIcon;

/**
 * 草稿箱
 */
public class DraftBox extends AbstractBox {
	@Override
	public String getText() {
		return "草稿箱";
	}

	public ImageIcon getImageIcon() {
		return super.getImageIcon("images/draft-tree.gif");
	}
}
