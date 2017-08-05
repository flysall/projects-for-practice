package flysall.email.box;

import javax.swing.ImageIcon;

/**
 * 导航树接口
 */
public interface MailBox {
	/**
	 * 获得名字
	 * @return 名字
	 */
	String getText();
	
	/**
	 * 返回对应图标
	 */
	ImageIcon getImageIcon();
}
