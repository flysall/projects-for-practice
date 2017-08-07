package flysall.email.ui;

import javax.swing.JOptionPane;
import flysall.email.object.*;

public class SendListMouseListener extends MainListMouseListener {

	@Override
	public void handle(FileObject file) {
		int result = JOptionPane.showOptionDialog(null, "请选择操作", "选择",
				0, JOptionPane.QUESTION_MESSAGE, null, 
				new Object[]{"打开", "取消"}, null);
		if (result == 0) {
			//打开操作
			openFile(file);
		}
	}

}
