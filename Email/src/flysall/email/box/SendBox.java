package flysall.email.box;

import javax.swing.ImageIcon;

public class SendBox extends AbstractBox{
	public String getText() {
		return "已发送";
	}
	public ImageIcon getImageIcon() {
		return super.getImageIcon("images/sent-tree.gif");
	}
}
