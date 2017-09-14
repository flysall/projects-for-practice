package com.jdbee.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.os.WindowsUtils;

import java.io.IOException;
import java.util.List;

/**
 * 用于处理网络请求
 */
public class HttpUtil {
	private static final Logger log = Logger.getLogger(JsoupUtil.class);

	public final String TAG = "HtttpUtils";
	public static CloseableHttpClient httpClient = HttpClients.createDefault();
	public static HttpClientContext context = new HttpClientContext();

	/**
	 * 使用Selenium模拟浏览器获取动态数据
	 * 
	 * @param url
	 *            网址
	 */
	public static Document getDocumentByUrl(String url) {
		WebDriver webDriver = null;
		Document document = null;
		try {
			System.getProperties().setProperty("webdriver.chrome.driver",
					"D:\\code\\Java\\JdBee-master\\src\\main\\resourceschromedriver.exe");
			webDriver = new ChromeDriver();
			webDriver.get(url);
			Thread.sleep(1000); // 停止一秒模拟网速
			document = Jsoup.parse(webDriver.getPageSource());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			webDriver.close();
			webDriver.quit();
		}
		return document;
	}

	/**
	 * 杀掉chrome后台进程，执行完就杀掉
	 */
	public static void killChromeDriver() {
		try {
			WindowsUtils.tryToKillByName("chromedriver.exe");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 模拟get请求
	 * 
	 * @param url
	 *            网址
	 * @param 网页源码
	 */
	public static String sendGet(String url) {
		CloseableHttpResponse response = null;
		String content = null;
		try {
			HttpGet get = new HttpGet(url);
			response = httpClient.execute(get, context);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity, "UTF-8");
			EntityUtils.consume(entity);
			return content;
		} catch (Exception e) {
			log.error("get请求获取数据失败，检查url");
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * 发送post请求
	 * 
	 * @param url
	 *            网址
	 * @return 网页源码
	 */
	public static String sendPost(String url, List<NameValuePair> nvps) {
		CloseableHttpResponse response = null;
		String content = null;

		try {
			HttpPost post = new HttpPost(url);
			// nvps是包装请求参数的url
			if (nvps != null) {
				post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			}
			response = httpClient.execute(post, context);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			return content;
		} catch (Exception e) {
			log.error("post请求数据失败， 检查url", e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
}
