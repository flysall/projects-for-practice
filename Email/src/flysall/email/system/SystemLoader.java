package flysall.email.system;

import java.util.*;

import flysall.email.object.*;
import flysall.email.ui.*;


/**
 * 加载本地邮件接口
 */
public interface SystemLoader {
	/**
	 * 根据MailContext得到对应收件箱邮件
	 */
	List<Mail> getInBoxMails(MailContext ctx);
	
	/**
	 * 根据MailContext得到对应发件箱邮件
	 */
	List<Mail> getOutBoxMails(MailContext ctx);
	
	/**
	 * 得到草稿箱邮件
	 */
	List<Mail> getDraftBoxMails(MailContext ctx);
	
	List<Mail> getSentBoxMails(MailContext ctx);
	/**
	 * 得到垃圾箱邮件
	 */
	List<Mail> getDeletedBoxMails(MailContext ctx);
}
