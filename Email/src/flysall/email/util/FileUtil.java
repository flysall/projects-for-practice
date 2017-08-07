package flysall.email.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.UUID;

import javax.mail.Part;
import javax.mail.internet.*;

import flysall.email.exception.*;
import flysall.email.object.*;
import flysall.email.ui.MailContext;

import com.thoughtworks.xstream.XStream;

/**
 * 文件工具类
 */
public class FileUtil {
	// 存放所有用户数据的目录
	public static final String DATE_FOLDER = "datas" + File.separator;
	// 存放具体某个用户配置的properties文件
	public static final String CONFIG_FILE = File.separator + "mail.properties";

	// 收件箱的目录名
	public static final String INBOX = "inbox";
	// 发件箱目录名
	public static final String OUTBOX = "outbox";
	// 已发送目录名
	public static final String SENT = "sent";
	// 草稿箱目录名
	public static final String DRAFT = "draft";
	// 垃圾箱目录名
	public static final String DELETED = "deleted";
	// 附件存放目录
	public static final String FILE = "file";

	/**
	 * 创建用户的账号目录和相关的子目录
	 * 
	 * @param ctx
	 *            邮件上下文
	 */
	public static void createFolder(MailContext ctx) {
		String accountRoot = getAccountRoot(ctx);
		mkdir(new File(accountRoot));
		mkdir(new File(accountRoot + INBOX));
		mkdir(new File(accountRoot + OUTBOX));
		mkdir(new File(accountRoot + SENT));
		mkdir(new File(accountRoot + DRAFT));
		mkdir(new File(accountRoot + DELETED));
		mkdir(new File(accountRoot + FILE));
	}

	/**
	 * 得到邮件账号的根目录
	 */
	private static String getAccountRoot(MailContext ctx) {
		String accountRoot = DATE_FOLDER + ctx.getUser() + File.separator + ctx.getAccount() + File.separator;
		return accountRoot;
	}

	/**
	 * 得到某个目录名字
	 */
	public static String getBoxPath(MailContext ctx, String folderName) {
		return getAccountRoot(ctx) + folderName + File.separator;
	}

	/**
	 * 为附件创建本地文件
	 */
	public static FileObject createFileFromPart(MailContext ctx, Part part) {
		try {
			// 得到文件存放目录
			String fileRepository = getBoxPath(ctx, FILE);
			String serverFileName = MimeUtility.decodeText(part.getFileName());
			// 生成UUID作为本地文件系统唯一的文件标识符
			String fileName = UUID.randomUUID().toString();
			File file = new File(fileRepository + fileName + getFileSufix(serverFileName));
			// 读写文件
			FileOutputStream fos = new FileOutputStream(file);
			InputStream is = part.getInputStream();
			BufferedOutputStream outs = new BufferedOutputStream(fos);
			// 如果附件内容为空part.getSize为-1, 如果直接new byte, 将抛出异常
			int size = (part.getSize() > 0) ? part.getSize() : 0;
			byte[] b = new byte[size];
			is.read(b);
			outs.write(b);
			outs.close();
			is.close();
			fos.close();
			// 封装对象
			FileObject fileObject = new FileObject(serverFileName, file);
			return fileObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileException(e.getMessage());
		}
	}

	/**
	 * 从相应的box中得到全部的xml文件
	 */
	public static List<File> getXMLFiles(MailContext ctx, String box) {
		String rootPath = getAccountRoot(ctx);
		String boxPath = rootPath + box;
		// 得到某个box目录
		File boxFolder = new File(boxPath);
		// 过滤
		List<File> files = filterFiles(boxFolder, ".xml");
		return files;
	}

	/**
	 * 在一个文件目录中，以文件名后缀过滤文件
	 */
	private static List<File> filterFiles(File folder, String sufix) {
		List<File> result = new ArrayList<File>();
		File[] files = folder.listFiles();
		if (files == null)
			return new ArrayList<File>();
		for (File file : files) {
			if (file.getName().endsWith(sufix))
				result.add(file);
		}
		return result;
	}

	/*
	 * 得到文件后缀名
	 */
	public static String getFileSufix(String fileName) {
		if (fileName == null || fileName.trim().equals(""))
			return "";
		if (fileName.indexOf(".") != -1) {
			return fileName.substring(fileName.indexOf("."));
		}
		return "";
	}

	// 创建XStream对象
	private static XStream xstream = new XStream();

	/**
	 * 将一个邮件对象使用XStream写到xml文件中
	 */
	public static void writeToXML(MailContext ctx, Mail mail, String boxFolder) {
		String xmlName = mail.getXmlName();
		String boxPath = getAccountRoot(ctx) + boxFolder + File.separator;
		File xmlFile = new File(boxPath + xmlName);
		writeToXML(xmlFile, mail);
	}

	/**
	 * 将一个mail对象写到xmlFile中
	 */
	public static void writeToXML(File xmlFile, Mail mail) {
		try {
			if (!xmlFile.exists())
				xmlFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(xmlFile);
			OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");  
			xstream.toXML(mail, writer);
			writer.close();
			fos.close();
		} catch (Exception e) {
			throw new FileException("写入文件异常：" + xmlFile.getAbsolutePath());
		}
	}

	/**
	 * 将一份xml文档转换为Mail对象
	 */
	public static Mail fromXML(MailContext ctx, File xmlFile){
		try {
			FileInputStream fis = new FileInputStream(xmlFile);
			Mail mail = (Mail)xstream.fromXML(fis);
			fis.close();
			return mail;
		} catch (Exception e) {
			throw new FileException("转换数据错误: " + xmlFile.getAbsolutePath());
		}
	}
	
	/*
	 * 复制文件
	 */
	public static void copy(File sourceFile, File targetFile) {
		try {
			Process process = Runtime.getRuntime().exec("cmd /c copy \"" +
					sourceFile.getAbsolutePath() + "\" \"" +
					targetFile.getAbsolutePath() + "\"");
			process.waitFor();
		} catch (Exception e) {
			throw new FileException("另存文件失败：" + targetFile.getAbsolutePath());
		}
	}
	
	/**
	 * 创建目录的工具方法， 判断目录是否存在
	 */
	private static void mkdir(File file) {
		if (!file.exists()) {
			file.mkdir();
		}
	}
}

















