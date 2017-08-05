package flysall.email.mail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Store;
import javax.mail.internet.MimeUtility;

import flysall.email.object.*;
import flysall.email.ui.*;
import flysall.email.util.*;


/**
 * 读取邮件实现类
 */
public class MailLoaderImpl implements MailLoader{
	@Override
	public List<Mail> getMessages(MailContext ctx) {
		Folder inbox = getINBOXFolder(ctx);
		try {
			inbox.open(Folder.READ_WRITE);
			Message[] messages = inbox.getMessages();
			List<Mail> result = getMailList(ctx, messages);
			sort(result);
			deleteFromServer(messages);
			inbox.close(true);
			return result;
		} catch (Exception e) {
			throw new LoadMailException(e.getMessage());
		}
	}
	
	/**
	 * 将mail中的Message对象转换为Mail对象
	 */
	private List<Mail> getMailList(MailContext ctx, Message[] messages) {
		List<Mail> result = new ArrayList<Mail>();
		try {
			for (Message m : messages) {
				String xmlName = UUID.randomUUID().toString() + ".xml";
				String content = getContent(m, new StringBuffer()).toString();
				Mail mail = new Mail(xmlName, getAllRecipients(m), getSender(m), 
						m.getSubject(), getReceivedDate(m), Mail.getSize(m.getSize()), hasRead(m), 
						content, FileUtil.INBOX);
				mail.setCcs(getCC(m));
				mail.setFiles(getFiles(ctx, m));
				result.add(mail);
			}
			return result;
		} catch (Exception e) {
			throw new LoadMailException(e.getMessage());
		}
	}
		
	//得到抄送的地址
	private List<String> getCC(Message m) throws Exception {
		Address[] addresses = m.getRecipients(Message.RecipientType.CC);
		return getAddresses(addresses);
	}
		
		
	/**
	 * 得到接收的日期
	 */
	private Date getReceivedDate(Message m) throws Exception {
		if(m.getSentDate() != null) 
			return m.getSentDate();
		if(m.getReceivedDate() != null) 
			return m.getReceivedDate();
		return new Date();
	}
	
	/**
	 * 获得邮件的附件
	 */
	private List<FileObject> getFiles(MailContext ctx, Message m) throws Exception{
		List<FileObject> files = new ArrayList<FileObject>();
		//若是复合类型，进行处理
		if (m.isMimeType("multipart/mixed")) {
			Multipart mp = (Multipart)m.getContent();
			int count = mp.getCount();
			for(int i = 0; i < count; i++) {
				Part part = mp.getBodyPart(i);
				files.add(FileUtil.createFileFromPart(ctx,  part));
			}
		}
		return files;
	}
	
	/**
	 * 返回邮件正文
	 */
	private StringBuffer getContent(Part part, StringBuffer result) throws Exception {
		if (part.isMimeType("multipart/*")) {
			Multipart p = (Multipart)part.getContent();
			int count = p.getCount();
			if (count > 1) 
				count = 1;
			for (int i = 0; i < count; i++) {
				BodyPart bp = p.getBodyPart(i);
				getContent(bp, result);
			}
		}
		else if (part.isMimeType("text/*")) {
			result.append(part.getContent());
		}
		return result;
	}
	
	
	/**
	 * 判断一份邮件是否已读
	 * @param m
	 * @return
	 * @throws Exception
	 */
	private boolean hasRead(Message m) throws Exception {
		Flags flags = m.getFlags();
		if (flags.contains(Flags.Flag.SEEN))
			return true;
		return false;
	}
	
	/**
	 * 得到一封邮件的所有收件人
	 */
	private List<String> getAllRecipients(Message m) throws Exception {
		Address[] addresses = m.getAllRecipients();
		return getAddresses(addresses);
	}
	
	/**
	 * 将参数的地址转换为字符串集合
	 */
	
	private List<String> getAddresses(Address[] addresses){
		List<String> result = new ArrayList<String>();
		if (addresses == null)
			return result;
		for(Address a : addresses) {
			result.add(a.toString());
		}
		return result;
	}
	
	/**
	 * 得到发件人地址
	 */
	private String getSender(Message m) throws Exception {
		Address[] addresses = m.getFrom();
		return MimeUtility.decodeText(addresses[0].toString());
	}
	
	/**
	 * 得到邮箱的INBOX
	 */
	private Folder getINBOXFolder(MailContext ctx) {
		Store store = ctx.getStore();
		try {
			return store.getFolder("INBOX");
		} catch (Exception e) {
			throw new LoadMailException("加载有相关错误，请检查配置");
		}
	}
	
	/**
	 * 将邮件数组设置为删除状态
	 */
	private void deleteFromServer(Message[] messages) throws Exception {
		for (Message m : messages) {
			m.setFlag(Flags.Flag.DELETED, true);
		}
	}
	
	/**
	 * 按时间排序
	 */
	private void sort(List<Mail> mails) {
		Collections.sort(mails, new MailComparator());
	}
}





























