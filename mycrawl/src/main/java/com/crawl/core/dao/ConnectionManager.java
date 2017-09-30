package com.crawl.core.dao;

import com.crawl.core.util.Config;
import com.crawl.core.util.SimpleLogger;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * DB Connection管理
 */
public class ConnectionManager {
	private static Logger logger = SimpleLogger.getSimpleLogger(ConnectionManager.class);
	private static Connection conn;
	public static Connection getConnection(){
		try{
			if(conn == null || conn.isClosed()){
				conn = createConnection();
			}
			else{
				return conn;
			}
		} catch(SQLException e){
			logger.error("SQLException", e);
		}
		return conn;
	}
	static{
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void close(){
		if(conn != null){
			try{
				conn.close();
			} catch(SQLException e) {
				logger.error("SQLException", e);
			}
		}
	}
	
	public static Connection createConnection(){
		String host = Config.dbHost;
		String user = Config.dbUsername;
		String password = Config.dbPassword;
		String dbName = Config.dbName;
		String url = "jdbc:mysql://" + host + ":3306/" + dbName + "?characterEncoding=utf8";
		Connection conn = null;
		try{
			conn= DriverManager.getConnection(url, user, password);
			logger.debug("success!");
		} catch(MySQLSyntaxErrorException e) {
			logger.error("数据库不存在.....请手动创建数据库");
			e.printStackTrace();
		} catch(SQLException e2){
			logger.error("SQLException", e2);
		}
		return conn;
	}
	public static void main(String[] args) {
		getConnection();
		getConnection();
		close();
	}
}





























