package loop;

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
			while (personValue.hasNext()) {
				nextURL = bURL + personValue.next().get(1) + eURL; // get(1)获得urltoke,
																	// 参考Follow
				LinkQueue.addUnvisited(nextURL);
			}
		}
	}

	public static void main(String[] args) {
		MyCrawler crawl = new MyCrawler("18846127825", "1514301jzc18");
		crawl.crawling("https://www.zhihu.com/people/flysall/following");
	}
}
