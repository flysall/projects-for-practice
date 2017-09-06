package loop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import resolve.ParseHtml;
import resolve.Seed;

public class Follow {
	private static Seed seed;
	private static String cookie;
	private static List<String> urlList;
	private static int count = 0;
	private static int followingNumber = 0;
	private RequestConfig globalConfig;
	private CloseableHttpClient httpClient;
	private static String bFollowingURL = "https://www.zhihu.com/api/v4/members/";
	private static String eFollowingURL = "/followees?include=data%5B%2A%5D.answer_count%2Carticles_count%2Cgender%2Cfollower_count%2Cis_followed%2Cis_following%2Cbadge%5B%3F%28type%3Dbest_answerer%29%5D.topics&";
	private List<Object> personInfo;
	private Map<Object, List<Object>> person;

	public List<Object> getPersonInfo() {
		return this.personInfo;
	}

	public Map<Object, List<Object>> getPerson() {
		return this.person;
	}

	public Follow(Seed seed) {
		globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).setConnectTimeout(5000)
				.setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
		httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
		this.seed = seed;
	}

	/**
	 * @param url
	 *            要解析的用户的网址， 获得该用户关注的所有人的信息
	 * @throws IOException 
	 */
	public void parseURL(String url) throws IOException {
		person = new LinkedHashMap<>();
		if (count == 0) {
			cookie = seed.getCookie("https://www.zhihu.com/login/phone_num");
			System.out.println(cookie);
			count++;
		}
		// 网址列表，包含了用户关注的人的所有信息
		List<String> urlList = handleURL(url, cookie);
		for (int i = 0; i < urlList.size(); i++) {
			HttpGet getMethod = new HttpGet(urlList.get(i));
			getMethod.setHeader("Cookie", cookie);
			HttpResponse response = null;
			String jsonContent = null;
			try {
				response = httpClient.execute(getMethod);
				if (response.getStatusLine().getStatusCode() == 200)
					if (response.getEntity() != null) {
						jsonContent = EntityUtils.toString(response.getEntity());
					}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
			for (int j = 0; j < 20 && j < followingNumber - 20 * i; j++) {
				personInfo = new ArrayList<>();
				JSONObject jsonObject = JSON.parseObject(jsonContent);
				JSONArray object = jsonObject.getJSONArray("data");
				JSONObject preName = object.getJSONObject(j);
				if (preName != null) {
					Object id = preName.get("id");
					Object name = preName.get("name");
					Object gender = preName.get("gender");
					Object user_type = preName.get("user_type");
					Object user_url = preName.get("url");
					Object avatar_url = preName.get("avatar_url");
					
					Object url_token = preName.get("url_token");
					String headline = (String) preName.get("headline");
					headline = headline.replaceAll("<a.*</a>", "(链接)");
					Object follower_count = preName.get("follower_count");
					Object is_followed = preName.get("is_followed");
					Object answer_count = preName.get("answer_count");
					Object articles_count = preName.get("articles_count");
					personInfo.add(id);
					personInfo.add(name);
					personInfo.add(gender);
					personInfo.add(user_type);
					personInfo.add(user_url);
					personInfo.add(url_token);
					personInfo.add(headline);
					personInfo.add(follower_count);
					personInfo.add(is_followed);
					personInfo.add(answer_count);
					personInfo.add(articles_count);
					personInfo.add(avatar_url);
					person.put(name, personInfo);
				}
			}
		}
	}

	/**
	 * @param url
	 *            用户关注页面网址
	 * @param cookie
	 *            用户Cookie
	 * @return 返回一个网址列表，这些网址列表中包含用户关注所有人的信息
	 */
	public static List<String> handleURL(String url, String cookie) {
		String html = Seed.visit(url, cookie);
		List<String> content = ParseHtml.basicInfo(html);
		String name = content.get(0);
		String followingURL;
		followingNumber = Integer.parseInt(content.get(1));
		int n = 0;
		urlList = new ArrayList<>();
		if (followingNumber > 20) {
			while (n < followingNumber) {
				followingURL = bFollowingURL + name + eFollowingURL + "limit=20&offset=" + n;
				urlList.add(followingURL);
				n += 20;
			}
		} else {
			followingURL = bFollowingURL + name + eFollowingURL + "limit=20&offset=0";
			urlList.add(followingURL);
		}
		return urlList;
	}

}
