package com.flysall.doubanMovieCrawler.movieCrawler.fetcher;

import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.flysall.doubanMovieCrawler.movieCrawler.model.CrawlerParams;
import com.flysall.doubanMovieCrawler.movieCrawler.model.FetchedPage;
import com.flysall.doubanMovieCrawler.movieCrawler.queue.UrlQueue;

public class PageFetcher {
	private static final Logger Log = Logger.getLogger(PageFetcher.class.getName());
	private HttpClient client;

	/**
	 * 创建HttpClient实例， 并初始化连接参数
	 */
	public PageFetcher() {
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
		HttpConnectionParams.setSoTimeout(params, 10 * 1000);
		client = new DefaultHttpClient(params);
	}

	/**
	 * 主动关闭httpClient连接
	 */
	public void close() {
		client.getConnectionManager().shutdown();
	}

	/**
	 * 根据url爬取网页内容 具体为将url、 由url获得的网页content、以及返回的http状态码statusCode作为参数
	 * 传递给FetchedPage构造器
	 * 
	 * @param url
	 * @return FetchedPage
	 */
	public FetchedPage getContentFromUrl(String url) {
		String content = null;
		String type = null;
		int statusCode = 500;
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		// host验证
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}

			public void verify(String arg0, SSLSocket arg1) throws IOException {
			}

			public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {
			}

			public void verify(String arg0, X509Certificate arg1) throws SSLException {
			}
		};

		// 创建Get请求，并设置Header
		HttpGet getHttp = new HttpGet(url);
		getHttp.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:16.0) Gecko/20100101 Firefox/16.0");
		HttpResponse response;
		Log.info("request url: " + url);
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别
			SSLContext ctx = SSLContext.getInstance("TLS");
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);
			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			socketFactory.setHostnameVerifier(hostnameVerifier);
			// 将SSLSocketFactory注册到HttpClient上
			client.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443));

			// 获得信息载体
			response = client.execute(getHttp);
			statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				content = EntityUtils.toString(entity, "UTF-8");
			}

			Header[] headers = response.getHeaders("Content-Type");
			String ContentType = headers[0].getValue();
			int start = ContentType.lastIndexOf("/") + 1;
			int end = ContentType.lastIndexOf(";");
			type = ContentType.substring(start, end);
		} catch (Exception e) {
			e.printStackTrace();
			// 如发生请求超时等异常，将URL返回待抓取队列，重新爬取
			Log.info(">> Put back url: " + url);
			UrlQueue.addFirstElement(url);
		}

		FetchedPage fetchedPage = null;
		if (type.equalsIgnoreCase("html")) {
			fetchedPage = new FetchedPage(url, content, statusCode, CrawlerParams.FETCHEDPAGETYPE_HTML);
		} else if (type.equalsIgnoreCase("json")) {
			fetchedPage = new FetchedPage(url, content, statusCode, CrawlerParams.FETCHEDPAGETYPE_JSON);
		}
		return fetchedPage;
	}
}
