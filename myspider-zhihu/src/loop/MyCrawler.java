package loop;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

import resolve.Seed;

public class MyCrawler {
	private String bURL = "https://www.zhihu.com/people/";
	private String eURL = "/following";
	private Seed seed;
	private Follow followList;
	private Map<Object, List<Object>> personMap; // 用户及每个用户的信息
	private Iterator<Object> personkey; // 可迭代类，用于遍历每一个用户
	private int count = 0;
	private int size = 0; // 每个用户信息的数目
	private static Properties config = new Properties();
	private PrintStream ps;
	int current = 0;
	private static boolean save = false; // 是否将爬取结果保存到本地
	// 加载配置文件
	static {
		try {
			InputStream is = new FileInputStream("config.properties");
			config.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		save = config.getProperty("save").equals("true");
	}
	
	public MyCrawler(String userName, String pwd) {
		this.seed = new Seed(userName, pwd);
		this.followList = new Follow(seed);
	}

	/**
	 * @param initialUrl
	 *            初始网址
	 */
	public void crawling(String initialUrl) {
		LinkQueue.addUnvisited(initialUrl);
		while (!LinkQueue.isUnVisitedEmpty() && LinkQueue.getVisitedSize() < 20) {
			String url = (String) LinkQueue.removeUnvisited();
			if (url == null)
				continue;
			followList.parseURL(url);
			personMap = followList.getPerson();
			Iterator<List<Object>> personValue = personMap.values().iterator();
			if (count == 0) {
				personkey = personMap.keySet().iterator();
				size = personMap.size();
				count++;
			}
			if (count == size) {
				System.out.print(personkey.next() + ":");
				count = 0;
			}
			personMap.size();
			LinkQueue.addVisited(url);
			String nextURL;
			if (MyCrawler.save == true) {
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
				nextURL = bURL + personValue.next().get(1) + eURL; // get(1)获得urltoke,
																	// 参考Follow
				List<Object> list = personMap.get(personkey.next());
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println("Information of User: " + list.get(1).toString());
				System.out.println("Id: " + list.get(0).toString());
				System.out.println("Name: " + list.get(1).toString());
				System.out.println("Gender: " + (list.get(2).toString().equals("1") ? "man" : "woman"));
				System.out.println("UserType: " + list.get(3).toString());
				System.out.println("Follower count: " + list.get(7).toString());
				System.out.println("Answer count: " + list.get(9).toString());
				System.out.println("Article count: " + list.get(10).toString());
				System.out.println("------------------");
				LinkQueue.addUnvisited(nextURL);
			}
		}
	}

	// 调试到Seed.getCookie
	public static void main(String[] args) {
		System.out.println("==> Hello, Please wait");
		String userName = config.getProperty("userName");
		String password = config.getProperty("password");
		String user_url = config.getProperty("user_url");
		MyCrawler crawl = new MyCrawler(userName, password);
		crawl.crawling(user_url);
	}
}
