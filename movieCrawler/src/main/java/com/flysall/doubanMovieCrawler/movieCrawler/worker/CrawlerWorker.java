package com.flysall.doubanMovieCrawler.movieCrawler.worker;

import java.util.logging.Logger;

import com.flysall.doubanMovieCrawler.movieCrawler.fetcher.PageFetcher;
import com.flysall.doubanMovieCrawler.movieCrawler.hadler.ContentHandler;
import com.flysall.doubanMovieCrawler.movieCrawler.model.FetchedPage;
import com.flysall.doubanMovieCrawler.movieCrawler.model.CrawlerParams;
import com.flysall.doubanMovieCrawler.movieCrawler.parser.ContentParser;
import com.flysall.doubanMovieCrawler.movieCrawler.queue.UrlQueue;
import com.flysall.doubanMovieCrawler.movieCrawler.storage.DataStorage;

public class CrawlerWorker implements Runnable {
	private static final Logger Log = Logger.getLogger(CrawlerWorker.class.getName());

	private PageFetcher fetcher;
	private ContentHandler handler;
	private ContentParser parser;
	private DataStorage store;
	private int threadIndex;

	public CrawlerWorker(int threadIndex) {
		this.threadIndex = threadIndex;
		this.fetcher = new PageFetcher();
		this.handler = new ContentHandler();
		this.parser = new ContentParser();
		this.store = new DataStorage();
	}

	public void run() {
		/**
		 * 登录，当待抓取的URL队列不为空时，执行爬取任务
		 */
		while (!UrlQueue.isEmpty()) {
			String url = UrlQueue.outElement();
			FetchedPage fetchedPage = fetcher.getContentFromUrl(url);
			// 检查页面爬取合法性
			if (!handler.check(fetchedPage)) {
				Log.info("Crawler-" + threadIndex + ": switch IP to");
				Log.info("The current url is: " + url);
				continue;
			}
			if (fetchedPage.getType() == CrawlerParams.FETCHEDPAGETYPE_JSON) {
				parser.parseJson(fetchedPage);
			} else if (fetchedPage.getType() == CrawlerParams.FETCHEDPAGETYPE_HTML) {
				Object targetData = parser.parse(fetchedPage);
			}

			// delay
			try {
				Thread.sleep(CrawlerParams.DEYLAY_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		fetcher.close();
		Log.info("Spider-" + threadIndex + ": strop...");
	}
}
