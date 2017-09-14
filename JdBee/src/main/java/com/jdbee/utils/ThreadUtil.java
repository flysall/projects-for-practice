package com.jdbee.utils;

import com.jdbee.model.FiveCategory;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {
	public static final Logger log = Logger.getLogger(ThreadUtil.class);
	private static List<Map<String, List<String>>> list = new ArrayList<Map<String, List<String>>>();
	private static List<List<String>> skus = new ArrayList<List<String>>();
	
	public static List<Map<String, List<String>>> getCategoryPageUrl(List<FiveCategory>){
		ExecutorService p = Executors.newFixedThreadPool(Constants.MAX_THREAD_CNT);
		final List<Callable<Integer>> partitions = new ArrayList<Callable<Integer>>();
		
		try{
			for(final FiveCategory category : categories){
				partitions.add(new Callable<Integer>(){
					
				})
			}
		}
	}
}
