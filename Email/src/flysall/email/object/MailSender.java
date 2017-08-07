package flysall.email.object;

import flysall.email.object.Mail;
import flysall.email.ui.MailContext;

/**
 * 发送邮件的接口
 */
public interface MailSender {
	/**
	 * 发送一封邮件并返回邮件对象
	 * @param mail 邮件对象
	 * @param ctx 邮件上下文
	 * @return 邮件对象
	 */
	Mail send(Mail mail, MailContext ctx);
}
