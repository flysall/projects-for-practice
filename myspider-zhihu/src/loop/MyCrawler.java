package loop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.*;

import resolve.Seed;
import util.Config;
import zhihuDao.ZhihuDaoImp;

public class MyCrawler {
	private String bURL = "https://www.zhihu.com/people/";
	private String eURL = "/following";
	private Seed seed;
	private Follow followList;
	private Map<Object, List<Object>> personMap; // 用户及每个用户的信息
	private Iterator<Object> personkey; // 可迭代类，用于遍历每一个用户
	private int count = 1;
	private int size = 0; // 每个用户信息的数目
	private PrintStream ps;
	int current = 0;
	private static ZhihuDaoImp dao;

	public MyCrawler(String userName, String pwd) {
		this.seed = new Seed(userName, pwd);
		this.followList = new Follow(seed);
		dao = new ZhihuDaoImp(); // 获得数据库
	}

	/**
	 * @param initialUrl
	 *            初始网址
	 * @throws IOException
	 */
	public void crawling(String initialUrl) throws IOException {
		LinkQueue.addUnvisited(initialUrl);
		while (!LinkQueue.isUnVisitedEmpty() && LinkQueue.getVisitedSize() <= Config.limit) {
			String url = (String) LinkQueue.removeUnvisited();
			if (url == null)
				continue;
			followList.parseURL(url);
			personMap = followList.getPerson();
			Iterator<List<Object>> personValue = personMap.values().iterator();
			personkey = personMap.keySet().iterator();
			size = personMap.size();
			personMap.size();
			LinkQueue.addVisited(url);
			String nextURL;
			// 将用户信息保存为文件
			if (Config.saveAsFile) {
				try {
					ps = new PrintStream(new FileOutputStream("user_information.txt"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("==> The information is printing to user_information.txt, Please check it!");
				System.setOut(ps);
			}
			while (personValue.hasNext()) {
				current++;
				nextURL = bURL + personValue.next().get(5) + eURL; // get(1)获得urltoke,
																	// 参考Follow
				List<Object> list = personMap.get(personkey.next());
				if (!Config.saveAsFile) {
					dao.insertUser(list);
					System.out.println("=====> 插入数据库成功， 用户信息如下：");
				}
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println("Information of User: " + list.get(1).toString());
				System.out.println("Name: " + list.get(1).toString());
				System.out.println("Gender: " + (list.get(2).toString().equals("1") ? "man" : "woman"));
				System.out.println("UserType: " + list.get(3).toString());
				System.out.println("Follower count: " + list.get(7).toString());
				System.out.println("Answer count: " + list.get(9).toString());
				System.out.println("Article count: " + list.get(10).toString());
				System.out.println("is_followed: " + list.get(8));
				System.out.println("is_following: " + list.get(12));
				System.out.println("homepage: " + list.get(13));
				System.out.println("------------------");
				LinkQueue.addUnvisited(nextURL);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println("==> Hello, Please wait");
		Config.init();
		MyCrawler crawl = new MyCrawler(Config.userName, Config.password);
		crawl.crawling(Config.userUrl);
		try{
			ZhihuDaoImp.cn.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
}
