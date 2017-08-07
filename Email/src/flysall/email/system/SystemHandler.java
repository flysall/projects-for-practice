package flysall.email.system;

import flysall.email.object.Mail;
import flysall.email.ui.MailContext;

public interface SystemHandler {
	
	/**
	 * 删除邮件
	 */
	void delete(Mail mail, MailContext ctx);
	
	/**
	 * 将邮件保存至已发送
	 */
	void saveSent(Mail mail, MailContext ctx);
	
	/**
	 * 将邮件保存至发件箱
	 * @param mail
	 * @param ctx 根据上下文得到相关的目录
	 */
	void saveOutBox(Mail mail, MailContext ctx);
	
	/**
	 * 保存至草稿箱
	 * @param mail
	 * @param ctx 根据上下文得到相关的目录
	 */
	void saveDraftBox(Mail mail, MailContext ctx);
	
	/**
	 * 彻底删除邮件
	 * @param mail
	 * @param ctx 根据上下文得到相关的目录
	 */
	void realDelete(Mail mail, MailContext ctx);
	
	/**
	 * 保存进InBox目录
	 * @param mail
	 * @param ctx
	 */
	void saveInBox(Mail mail, MailContext ctx);
	
	/**
	 * 保存一封邮件，该邮件已经存在于本地的目录中
	 * @param mail
	 * @param ctx
	 */
	void saveMail(Mail mail, MailContext ctx);
	
	/**
	 * 将在垃圾箱中的邮件还原
	 * @param mail
	 * @param ctx
	 */
	void revert(Mail mail, MailContext ctx);
}


