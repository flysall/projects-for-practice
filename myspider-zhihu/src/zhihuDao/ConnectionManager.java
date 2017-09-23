package zhihuDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import util.Config;

public class ConnectionManager {
	private static Connection conn;

	/**
	 * 加载mysql驱动
	 */
	public static void init(){
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接（单例类）
	 * 
	 * @return 返回一个数据库连接
	 */
	public static Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				conn = createConnection();
			} else {
				return conn;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static Connection createConnection() {
		String host = Config.dbHost;
		String user = Config.dbUsername;
		String password = Config.dbPassword;
		String dbName = Config.dbName;
		String url = "jdbc:mysql://" + host + ":3306/" + dbName + "?characterEncoding=utf-8";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("====> 创建数据库成功");
		} catch (MySQLSyntaxErrorException e) {
			System.out.println("====> 创建数据库失败， 请检查配置");
			e.printStackTrace();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return conn;
	}

	public static boolean close() {
		try {
			if (conn != null || conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
}
