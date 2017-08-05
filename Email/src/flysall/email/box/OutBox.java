package flysall.email.box;

import javax.swing.*;

public class OutBox extends AbstractBox {
	public String getText() {
		return "发件箱";
	}
	public ImageIcon getImageIcon() {
		return super.getImageIcon("images/out-tree.gif");
	}
}
