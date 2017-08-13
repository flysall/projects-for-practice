package com.crawl.core.httpclient;

public interface IHttpClient {

	/**
	 * 初始化客户端
	 * 模拟登陆，持久化Cookie到本地
	 */
	abstract void initHttpClient();
	
	/**
	 * 爬虫入口
	 */
	void startCrawl(String url);
	
	/**
	 * 爬虫入口
	 */
	void startCrawl();
}
