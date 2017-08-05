package flysall.email.mail;

import flysall.email.object.Mail;
import flysall.email.ui.MailContext;

/**
 * 发送邮件接口
 */
public interface MailSender {
	/**
	 * 发送一封邮件并返回邮件对象
	 */
	Mail send(Mail mail, MailContext ctx);
}
