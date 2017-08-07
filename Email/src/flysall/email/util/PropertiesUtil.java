package flysall.email.util;

import java.io.*;
import java.util.Properties;

import flysall.email.exception.PropertiesException;
import flysall.email.ui.MailContext;

/**
 * 属性工具类
 */
public class PropertiesUtil {
	/**
	 * 根据文件得到对应的Properties
	 */
	private static Properties getProperties(File propertyFile) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(propertyFile);
		prop.load(fis);
		fis.close();
		return prop;
	}
	
	/**
	 * 根据配置文件的对象构造MailContext对象
	 */
	public static MailContext createContext(File propertyFile) throws IOException {
		Properties props = getProperties(propertyFile);
		Integer smtpPort = getInteger(props.getProperty("smtpPort"), 25);
		Integer pop3Port = getInteger(props.getProperty("pop3Port"), 110);
		return new MailContext(null, 
				props.getProperty("account"), props.getProperty("password"),
				props.getProperty("smtpHost"), smtpPort,
				props.getProperty("pop3Host"), pop3Port);
	}
	
	/**
	 * 将int封装成Integer
	 */
	private static Integer getInteger(String s, int defaultValue) {
		if (s == null || s.trim() == "") 
			return defaultValue;
		return Integer.parseInt(s);
	}
	
	/**
	 * 保存一个MailContext对象， 将它写入属性文件中
	 */
	public static void store(MailContext ctx) {
		try {
			File propFile = new File(FileUtil.DATE_FOLDER + ctx.getUser() +
					FileUtil.CONFIG_FILE);
			Properties prop = getProperties(propFile);
			prop.setProperty("account", ctx.getAccount());
			prop.setProperty("password", ctx.getPassword());
			prop.setProperty("smtpHost", ctx.getSmtpHost());
			prop.setProperty("smtpPort",  String.valueOf(ctx.getSmtpPort()));
			prop.setProperty("pop3Host", ctx.getPop3Host());
			prop.setProperty("prop3Port", String.valueOf(ctx.getPop3Port()));
			FileOutputStream fos = new FileOutputStream(propFile);
			prop.store(fos, "There are mail config");
			fos.close();
		} catch (Exception e) {
			throw new PropertiesException("");
		}
	}
	
}




















