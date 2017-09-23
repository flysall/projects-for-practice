package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	/**
	 * 知乎用户名
	 */
	public static String userName;
	
	/**
	 * 知乎密码
	 */
	public static String password;
	
	/**
	 * 种子url
	 */
	public static String userUrl;
	
	/**
	 * 是否将cookie保存到本地，如果为true，则登录之后下次运行可免登录
	 */
	public static boolean cookieSave;
	/*
	 * 选择是保存到文件还是数据库，true为文件， false为数据库
	 */
	public static boolean saveAsFile;

	/**
	 * 数据库名
	 */
	public static String dbName;

	/**
	 * 数据库用户名
	 */
	public static String dbUsername;

	/**
	 * 数据库密码
	 */
	public static String dbPassword;

	/**
	 * 数据库host
	 */
	public static String dbHost;
	
	/**
	 * 用于限制爬取的用户数目
	 */
	public static int limit;

	public static void init(){
		Properties p = new Properties();
		try {
			InputStream is = new FileInputStream("config.properties"); 
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		userName = p.getProperty("username");
		password = p.getProperty("password");
		userUrl = p.getProperty("user_url");
		limit = Integer.valueOf(p.getProperty("limit"));
		saveAsFile = Boolean.parseBoolean(p.getProperty("saveasfile"));
		cookieSave = Boolean.parseBoolean(p.getProperty("cookiesave"));
		if (!saveAsFile){
			dbName = p.getProperty("db.name");
			dbHost = p.getProperty("db.host");
			dbUsername = p.getProperty("db.username");
			dbPassword = p.getProperty("db.password");
		}
	
	}

}
