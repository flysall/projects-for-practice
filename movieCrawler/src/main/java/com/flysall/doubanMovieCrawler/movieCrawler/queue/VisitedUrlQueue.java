package com.flysall.doubanMovieCrawler.movieCrawler.queue;

import java.util.LinkedList;

public class VisitedUrlQueue {
	private static LinkedList<String> visitedUrlQueue = new LinkedList<String>();

	public synchronized static void addElement(String url) {
		visitedUrlQueue.add(url);
	}

	public synchronized static void addFirstElement(String url) {
		visitedUrlQueue.addFirst(url);
	}

	public synchronized static String outElement() {
		return visitedUrlQueue.removeFirst();
	}

	public synchronized static boolean isEmtpy() {
		return visitedUrlQueue.isEmpty();
	}

	public static int size() {
		return visitedUrlQueue.size();
	}

	public boolean isContains(String url) {
		return visitedUrlQueue.contains(url);
	}
}
