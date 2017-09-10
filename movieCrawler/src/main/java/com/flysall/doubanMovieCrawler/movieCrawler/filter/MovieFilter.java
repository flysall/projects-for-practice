package com.flysall.doubanMovieCrawler.movieCrawler.filter;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.flysall.doubanMovieCrawler.movieCrawler.model.Movie;

/**
 * 过滤器，获得满足条件的电影
 */
public class MovieFilter {
	private static final Logger Log = Logger.getLogger(MovieFilter.class.getName());
	/**
	 * 筛选条件，评分大于7筛选出来
	 * @param movie
	 * @return
	 */
	public static boolean isMatch(Movie movie){
		Movie defineMovie = new Movie("", "^[7-9]\\d*\\.\\d$");
		Pattern pattern = Pattern.compile(defineMovie.getRate());
		Matcher matcher = pattern.matcher(movie.getRate());
		Boolean isMatch = matcher.matches();
		Log.info("MovieFilter match: " + isMatch);
		return isMatch;
	}
}
