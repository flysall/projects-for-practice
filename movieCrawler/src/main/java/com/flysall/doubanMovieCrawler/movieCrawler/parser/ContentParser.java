package com.flysall.doubanMovieCrawler.movieCrawler.parser;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.flysall.doubanMovieCrawler.movieCrawler.filter.MovieFilter;
import com.flysall.doubanMovieCrawler.movieCrawler.model.FetchedPage;
import com.flysall.doubanMovieCrawler.movieCrawler.model.Movie;
import com.flysall.doubanMovieCrawler.movieCrawler.queue.UrlQueue;
import com.flysall.doubanMovieCrawler.movieCrawler.queue.VisitedUrlQueue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ContentParser {
	public Object parse(FetchedPage fetchedPage) {
		Object targetObject = null;
		String name = null;
		String time = null;
		String countroy = null;
		String rate = null;
		String summary = null;
		String photo = null;

		Document doc = Jsoup.parse(fetchedPage.getContent());
		System.out.println("========movie info start ========");
		if (doc.getElementsByAttributeValue("property", "v:itemreviewed") != null) {
			name = doc.getElementsByAttributeValue("property", "v:itemreviewed").html();
			System.out.println("name: " + name);
		}
		if (doc.getElementsByAttributeValue("property", "v:initialReleaseDate") != null) {
			time = doc.getElementsByAttributeValue("property", "v:initialReleaseDate").html();
			System.out.println("time: " + time);
		}
		if (doc.getElementsByAttributeValue("property", "v:average") != null) {
			rate = doc.getElementsByAttributeValue("property", "v:average").html();
			System.out.println("rate: " + rate);
		}
		if (doc.getElementsByAttributeValue("property", "v:summary").html() != null) {
			summary = doc.getElementsByAttributeValue("property", "v:summary").html();
			System.out.println("summary: " + summary);
		}
		if (doc.getElementsByAttributeValue("rel", "v:image").attr("src") != null) {
			photo = doc.getElementsByAttributeValue("rel", "v:image").attr("src");
		}
		System.out.println("========movie info end========");
		// 将URL放入已爬取的队列
		VisitedUrlQueue.addElement(fetchedPage.getUrl());
		return targetObject;
	}

	/**
	 * 解析json对象
	 * 
	 * @param fetchedPage
	 */
	public void parseJson(FetchedPage fetchedPage) {
		JSONObject obj = JSON.parseObject(fetchedPage.getContent());
		Object subjects = obj.get("subjects");
		List<Object> list = JSON.parseArray(subjects.toString());
		for (Object object : list) {
			Map movieMap = JSON.parseObject(object.toString(), Map.class);
			// 过滤数据
			String name = movieMap.get("title").toString();
			String url = movieMap.get("url").toString();
			String rate = movieMap.get("rate").toString();
			Movie movie = new Movie(name, rate);

			// 把过滤后合适的数据，放入url队列中
			if (MovieFilter.isMatch(movie)) {
				UrlQueue.addElement(url);
			}
		}
		// 将URL放入已爬取队列
		VisitedUrlQueue.addElement(fetchedPage.getUrl());
	}
}
