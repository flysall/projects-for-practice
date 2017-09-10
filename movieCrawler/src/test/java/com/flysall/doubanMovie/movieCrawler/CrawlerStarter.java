package com.flysall.doubanMovie.movieCrawler;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.flysall.doubanMovieCrawler.movieCrawler.model.CrawlerParams;
import com.flysall.doubanMovieCrawler.movieCrawler.queue.UrlQueue;
import com.flysall.doubanMovieCrawler.movieCrawler.worker.CrawlerWorker;

public class CrawlerStarter {
	public static void main(String[] args) {
		// 初始化配置参数
		initializeParams();

		// 初始化爬虫队列
		initializeQueue();

		// 创建worker并启动
		for (int i = 1; i <= CrawlerParams.WORKER_NUM; i++) {
			new Thread(new CrawlerWorker(i)).start();
		}
	}

	/**
	 * 初始化配置文件餐数据
	 */
	private static void initializeParams() {
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream("conf/crawler.properties"));
			Properties properties = new Properties();
			properties.load(in);

			CrawlerParams.WORKER_NUM = Integer.parseInt(properties.getProperty("crawler.threadNum"));
			CrawlerParams.DEYLAY_TIME = Integer.parseInt(properties.getProperty("crawler.fetchDelay"));
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用于初始化的爬虫连接
	 */
	private static void initializeQueue() {
		for (int i = 0; i < 2; i++) {
			UrlQueue.addElement(
					"http://movie.douban.com/j/search_subjects?type=movie&tag=%E7%83%AD%E9%97%A8&sort=recommend&page_limit=20&page_start="
							+ i * 20 + 1);
		}
	}
}
