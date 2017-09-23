package zhihuDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ZhihuDao {
	boolean insertUser(List user);
}
