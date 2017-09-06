package resolve;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import loop.MyCrawler;

/**
 * 获取Cookie
 */
public class Seed {
	private RequestConfig globalConfig;
	private static CloseableHttpClient httpClient;
	private PoolingHttpClientConnectionManager cm;
	private String xsrfValue;
	private String captURL = "https://www.zhihu.com/captcha.gif?r=" + System.currentTimeMillis() + "&type=login";
	private String userName = "";
	private String passWord = "";
	private boolean cookieSave = false; 
	private FileInputStream fis = null;
	private File cookieFile = null;

	public String getName() {
		return this.userName;
	}

	public String getPassword() {
		return this.passWord;
	}

	public CloseableHttpClient getHttpClient() {
		return this.httpClient;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public Seed(String userName, String passWord) {
		// setConnectTimeout: 设置连接超时时间，单位为毫秒
		// setConnectionRequestTimeout: 设置从connect Manager获取Connection超时时间，单位为毫秒
		// setSocketTimeOut: 请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
		globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).setConnectTimeout(5000)
				.setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
		cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(20);
		cm.setDefaultMaxPerRoute(20); // 每个路由基础的连接增加到20
		cm.closeExpiredConnections();
		httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).setConnectionManager(cm).build();
		this.userName = userName;
		this.passWord = passWord;
	}

	/**
	 * @param url
	 *            登录页面网址
	 * @return 返回用户Cookie
	 * @throws IOException 
	 */
	public String getCookie(String url) throws IOException {
		HttpGet getMethod = new HttpGet(url);
		getMethod.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		getMethod.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		getMethod.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		getMethod.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
		CloseableHttpResponse status = null;
		String cookie = null;
		String responseHtml = null;
		HttpResponse loginstatus = null;
		String loginCookie = null;
		cookieSave = MyCrawler.getCookieSave();
		URI u = null;
		cookieFile = new File("D:/cookie.txt");

		if(cookieFile.exists() == false || cookieSave == false){
			try {
				status = httpClient.execute(getMethod);
				cookie = getCookie(status, "cookie");
				responseHtml = EntityUtils.toString(status.getEntity());
				xsrfValue = responseHtml.split("<input type=\"hidden\" name=\"_xsrf\" value=\"")[1].split("\"/>")[0];
				String captcha = getCaptcha(httpClient, cookie);
				u = new URIBuilder(url).addParameter("_xsrf", xsrfValue).addParameter("phone_num", userName)
						.addParameter("password", passWord).addParameter("captcha", captcha).build();
				HttpPost postMethod = new HttpPost(u);
				postMethod.setHeader("Cookie", cookie);
				loginstatus = httpClient.execute(postMethod);
				if (loginstatus.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
					Header head = loginstatus.getFirstHeader("Location");
					String strHead = head.getValue();
					HttpPost rePostMethod = new HttpPost(strHead);
					loginstatus = httpClient.execute(rePostMethod);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			loginCookie = getCookie(loginstatus, "loginCookie");
		} else{
			System.out.println("==> we have cookie, don't need to login");
			String login = new String(Files.readAllBytes(Paths.get("D:/cookie.txt")));
		}
		return loginCookie;
	}

	/**
	 * @param httpResponse服务器回应消息
	 * @return 返回用户cookie
	 */
	public String getCookie(HttpResponse httpResponse, String flag) {
		Map<String, String> cookieMap = new HashMap<String, String>(64);
		Header[] headers = httpResponse.getHeaders("Set-Cookie");
		if (headers == null || headers.length == 0) {
			System.out.println("--------There is not cookie");
			return null;
		}
		String cookie = "";
		for (int i = 0; i < headers.length; i++) {
			cookie += headers[i].getValue();
			if (i != headers.length - 1) {
				cookie += ";";
			}
		}
		
		String[] cookies = cookie.split(";");
		for (String c : cookies) {
			c = c.trim();
			cookieMap.put(c.split("=")[0],
					c.split("=").length == 1 ? "" : (c.split("=").length == 2 ? c.split("=")[1] : c.split("=", 2)[1]));// 最多分割2-1次
		}
		String cookiesTmp = "";
		for (String key : cookieMap.keySet()) {
			cookiesTmp += key + "=" + cookieMap.get(key) + ";";
		}
		if(cookieSave == true && flag.equals("loginCookie")){
			try{
				FileOutputStream fos = new FileOutputStream(cookieFile);
				PrintStream ps = new PrintStream(fos);
				System.setOut(ps);
				System.out.println(cookiesTmp);
				ps.close();
				fos.close();
			} catch (IOException e){
				e.printStackTrace();
			} 
		}
		return cookiesTmp.substring(0, cookiesTmp.length() - 2);
	}

	/**
	 * @param httpClient
	 *            设置的httpClient
	 * @param cookie
	 *            用户登录的cookie
	 * @return 返回验证码
	 */
	public String getCaptcha(CloseableHttpClient httpClient, String cookie) {
		HttpGet getCaptcha = new HttpGet(captURL);
		getCaptcha.setHeader("Cookie", cookie);
		CloseableHttpResponse imageResponse = null;
		try {
			imageResponse = httpClient.execute(getCaptcha);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileOutputStream out;
		try {
			out = new FileOutputStream("d:/captcha.jpg");
			int imgCont;
			while ((imgCont = imageResponse.getEntity().getContent().read()) != -1) {
				out.write(imgCont);
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("请输入验证码:");
		Scanner sc = new Scanner(System.in);
		String captcha = sc.next();
		System.out.println("==> Please wait again!");
		return captcha;
	}

	/**
	 * @param url
	 *            用户关注页面的网址
	 * @return 用户关注页面的网页源代码
	 */
	public static String visit(String url, String cookie) {
		HttpGet get = new HttpGet(url);
		get.setHeader("Cookie", cookie);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(get);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String responseHtml = null;
		if (response.getStatusLine().getStatusCode() == 200) {
			try {
				responseHtml = EntityUtils.toString(response.getEntity(), "utf-8");
			} catch (IOException | UnsupportedOperationException e) {
				e.printStackTrace();
			}
		}
		return responseHtml;
	}
}
