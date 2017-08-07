package flysall.email.system;

import java.io.File;
import java.util.*;

import flysall.email.object.*;
import flysall.email.system.SystemHandler;
import flysall.email.ui.*;
import flysall.email.util.*;

/**
 * 本地邮件处理类
 */
public class SystemHandlerImpl {
	public void delete(Mail mail, MailContext ctx) {
		File file = getMailXmlFile(mail.getXmlName(), ctx);
		file.delete();
		FileUtil.writeToXML(ctx, mail, FileUtil.DELETED);
	}

	public void realDelete(Mail mail, MailContext ctx) {
		File xmlFile = getMailXmlFile(mail.getXmlName(), ctx);
		List<FileObject> files = mail.getFiles();
		for (FileObject f : files)
			f.getFile().delete();
		if (xmlFile.exists())
			xmlFile.delete();
	}

	public void saveDraftBox(Mail mail, MailContext ctx) {
		saveFiles(mail, ctx);
		FileUtil.writeToXML(ctx, mail, FileUtil.DRAFT);
	}

	public void saveInBox(Mail mail, MailContext ctx) {
		FileUtil.writeToXML(ctx, mail, FileUtil.INBOX);
	}

	public void saveOutBox(Mail mail, MailContext ctx) {
		saveFiles(mail, ctx);
		FileUtil.writeToXML(ctx, mail, FileUtil.OUTBOX);
	}

	public void saveSent(Mail mail, MailContext ctx) {
		saveFiles(mail, ctx);
		FileUtil.writeToXML(ctx, mail, FileUtil.SENT);
	}

	/**
	 * 保存Mail对象中的附件
	 */
	private void saveFiles(Mail mail, MailContext ctx) {
		List<FileObject> files = mail.getFiles();
		List<FileObject> newFiles = new ArrayList<FileObject>();
		int byteSize = mail.getContent().getBytes().length;
		for (FileObject f : files) {
			String sentBoxPath = FileUtil.getBoxPath(ctx, FileUtil.FILE);
			String fileName = UUID.randomUUID().toString();
			String sufix = FileUtil.getFileSufix(f.getFile().getName());
			File targetFile = new File(sentBoxPath + fileName + sufix);
			FileUtil.copy(f.getFile(), targetFile);
			newFiles.add(new FileObject(f.getSourceName(), targetFile));
			byteSize += targetFile.length();
		}
	}

	public void saveMail(Mail mail, MailContext ctx) {
		File xmlFile = getMailXmlFile(mail.getXmlName(), ctx);
		FileUtil.writeToXML(xmlFile, mail);
	}

	public void revert(Mail mail, MailContext ctx) {
		File xmlFile = getMailXmlFile(mail.getXmlName(), ctx);
		xmlFile.delete();
		FileUtil.writeToXML(ctx, mail, mail.getFrom());
	}

	/**
	 * 从所有的邮件中查找名为xmlName的xml文件
	 * 
	 * @param xmlName
	 * @param ctx
	 * @return
	 */
	private File getMailXmlFile(String xmlName, MailContext ctx) {
		List<File> allXMLFiles = getAllFiles(ctx);
		for (File f : allXMLFiles) {
			if (f.getName().equals(xmlName))
				return f;
		}
		return null;
	}

	private List<File> getAllFiles(MailContext ctx) {
		List<File> inboxXmls = FileUtil.getXMLFiles(ctx, FileUtil.INBOX);
		List<File> outboxXmls = FileUtil.getXMLFiles(ctx, FileUtil.OUTBOX);
		List<File> draftXmls = FileUtil.getXMLFiles(ctx, FileUtil.DRAFT);
		List<File> sentXmls = FileUtil.getXMLFiles(ctx, FileUtil.SENT);
		List<File> deletedXmls = FileUtil.getXMLFiles(ctx, FileUtil.DELETED);
		List<File> result = new ArrayList<File>();
		result.addAll(inboxXmls);
		result.addAll(outboxXmls);
		result.addAll(draftXmls);
		result.addAll(sentXmls);
		result.addAll(deletedXmls);
		return result;
	}

}
