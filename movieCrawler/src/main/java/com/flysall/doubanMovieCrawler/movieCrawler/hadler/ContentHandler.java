package com.flysall.doubanMovieCrawler.movieCrawler.hadler;

import com.flysall.doubanMovieCrawler.movieCrawler.model.FetchedPage;
import com.flysall.doubanMovieCrawler.movieCrawler.queue.UrlQueue;

public class ContentHandler {
	/**
	 * 检查是否限制爬虫，若没有，返回true
	 * @param fetchedPage
	 * @return
	 */
	public boolean check(FetchedPage fetchedPage) {
		// 若爬取的页面包含反爬取内容，则将当前URL放入待爬取队列，以待重新爬取
		if (isAntiScratch(fetchedPage)) {
			UrlQueue.addFirstElement(fetchedPage.getUrl());
			return false;
		}
		return true;
	}

	private boolean isStatusValid(int statusCode) {
		if (statusCode >= 200 && statusCode < 400) {
			return true;
		}
		return false;
	}

	private boolean isAntiScratch(FetchedPage fetchedPage) {
		// 403 forbidden
		if ((!isStatusValid(fetchedPage.getStatusCode())) && fetchedPage.getStatusCode() == 403) {
			return true;
		}

		// 页面包含的反爬取内容
		if (fetchedPage.getContent().contains("<div>禁止访问</div>")) {
			return true;
		}
		return false;
	}
}
