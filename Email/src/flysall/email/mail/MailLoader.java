package flysall.email.mail;

import java.util.List;

import flysall.email.object.Mail;
import flysall.email.ui.MailContext;

/**
 * 读取邮件信息的接口
 */
public interface MailLoader {
	/**
	 * 得到INBOX中所有的邮件
	 * @param ctx 邮箱的上下文
	 * @return 上下文的集合
	 */
	List<Mail> getMessages(MailContext ctx);
}
