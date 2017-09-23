package zhihuDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.*;

import zhihuDao.ConnectionManager;

import java.sql.PreparedStatement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class ZhihuDaoImp implements ZhihuDao{
	public static Connection cn;
	private static int primId = 1; //用于充当数据库中的id值
	static{
		ConnectionManager.init();
		cn = ConnectionManager.getConnection();
	}
	
	public boolean insertUser(List u){
		try{
			String column = "id, name, gender, usertype, follower_count, answer_count, article_count, is_followed, is_following, homepage";
			String value = "?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
			String sql = "insert into user (" + column + ") values(" + value + ")";
			PreparedStatement pstmt;
			pstmt = cn.prepareStatement(sql);
			pstmt.setInt(1, primId);
			pstmt.setString(2, u.get(1).toString());
			pstmt.setString(3, u.get(2).toString().equals("1") ? "man" : "woman");
			pstmt.setString(4, u.get(3).toString());
			pstmt.setInt(5, (Integer)u.get(7));
			pstmt.setInt(6, (Integer)u.get(9));
			pstmt.setInt(7, (Integer)u.get(10));
			pstmt.setString(8, u.get(8).toString());
			pstmt.setString(9, u.get(12).toString());
			pstmt.setString(10, u.get(13).toString());
			pstmt.executeUpdate();
			primId++;
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			
		}
		return true;
	}
}
