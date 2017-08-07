package flysall.email.system;

import java.io.File;
import java.util.*;

import flysall.email.object.*;
import flysall.email.ui.*;
import flysall.email.util.FileUtil;

/**
 * 本地邮件加载实现
 */
public class SystemLoaderImpl implements SystemLoader {
	//垃圾箱
	public List<Mail> getDeletedBoxMails(MailContext ctx) {
		return getMails(ctx, FileUtil.DELETED);
	}
	
	//草稿箱
	public List<Mail> getDraftBoxMails(MailContext ctx) {
		return getMails(ctx, FileUtil.DRAFT);
	}
	
	//收件箱
	public List<Mail> getInBoxMails(MailContext ctx) {
		return getMails(ctx, FileUtil.INBOX);
	}
	
	/**
	 * 将xml文件转换为Mail对象，并排序
	 */
	private List<Mail> convert(List<File> xmlFiles, MailContext ctx) {
		List<Mail> result = new ArrayList<Mail>();
		for (File file : xmlFiles) {
			Mail mail = FileUtil.fromXML(ctx, file);
			result.add(mail);
		}
		sort(result);
		return result;
	}
	
	/**
	 * 按时间进行排序
	 */
	@SuppressWarnings("unchecked")
	private void sort(List<Mail> mails){
		Collections.sort(mails, new MailComparator());
	}
	
	/**
	 * 得到发件箱邮件
	 */
	public List<Mail> getOutBoxMails(MailContext ctx) {
		return getMails(ctx, FileUtil.OUTBOX);
	}
	
	/**
	 * 得到已发送邮件
	 */
	public List<Mail> getSentMails(MailContext ctx) {
		return getMails(ctx, FileUtil.SENT);
	}
	
	private List<Mail> getMails(MailContext ctx, String box) {
		List<File> xmlFiles = FileUtil.getXMLFiles(ctx, box);
		List<Mail> result = convert(xmlFiles, ctx);
		return result;
	}
}




















