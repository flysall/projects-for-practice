package com.crawl.zhihu.task;

import com.crawl.core.dao.ConnectionManager;
import com.crawl.core.parser.DetailPageParser;
import com.crawl.core.parser.ListPageParser;
import com.crawl.core.util.Config;
import com.crawl.core.util.Md5Util;
import com.crawl.core.util.SimpleInvocationHandler;
import com.crawl.core.util.SimpleLogger;
import com.crawl.zhihu.ZhiHuHttpClient;
import com.crawl.zhihu.entity.Page;
import com.crawl.zhihu.entity.User;
import com.crawl.zhihu.parser.ZhiHuNewUserDetailPageParser;
import com.crawl.zhihu.parser.ZhiHuUserListPageParser;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import static com.crawl.core.util.Constants.USER_FOLLOWEES_URL;
import static com.crawl.zhihu.ZhiHuHttpClient.parseUserCount;

/**
 * 知乎详情页task
 */
public class DetailPageTask extends AbstractPageTask{
	private static Logger logger = SimpleLogger.getSimpleLogger(DetailListPageTask.class);
	private static DetailPageParser proxyDetailPageParser;
	static {
		proxyDetailPageParser = getProxyDetailParser();
	}
	public DetailListPageTask(String url, boolean proxyFlag){
		super(url, proxyFlag);
	}
	
	@Override
	void retry(){
		zhiHuHttpClient.getDetailPageThreadPool().execute(new DetailListPageTask(url, Config.isProxy));
	}
	
	@Override 
	void handle(Page page){
		DetailPageParser parser = null;
//      parser = ZhiHuNewUserDetailPageParser.getInstance();
		parser = proxyDetailPageParser;
		User u = parser.parseDetailPage(page);
		logger.info("解析用户成功:" + u.toString());
		if(Config.dbEnable){
			zhiHuDao1.insertUser(u);
		}
		parseUserCount.incrementAndGet();
		for(int i = 0; i < u.getFollowees() / 20 + 1; i++){
			String userFolloweesUrl = formatUserFolloweesUrl(u.getUserToken(), 20 * i);
			handleUrl(userFolloweesUrl);
		}
	}
	
	public String formatUserFolloweesUrl(String userToken, int offset){
		String url = "https://www.zhihu.com/api/v4/members/" + userToken + "/followees?include=data%5B*%5D.answer_count%2Carticles_count%2Cfollower_count%2C" +
                "is_followed%2Cis_following%2Cbadge%5B%3F(type%3Dbest_answerer)%5D.topics&offset=" + offset + "&limit=20";
        return url;
	}
	
	private void handleUrl(String url){
		HttpGet request = new HttpGet(url);
		request.setHeader("authorization", "oauth" + zhiHuHttpClient.getAuthorization());
		if(Config.dbEnable){
			zhiHuHttpClient.getListPageThreadPool().execute(new ListPageTask(request, Config.isProxy));
			return;
		}
		zhiHuHttpClient.getListPageThreadPool().execute(new ListPageTask(request, Config.isProxy));
	}
	
	/**
	 * 代理类
	 */
	private static DetailPageParser getProxyyDetailParser(){
		DetailPageParser detailPageParser = ZhiHuNewUserDetailPageParser.getInstance();
		InvocationHandler invocationHandler = new SimpleInvocationHandler(detailPageParser);
		DetailPageParser proxyDetailPageParser = (DetailPageParser) Proxy.newProxyInstance(detailPageParser.getClass().getClassLoader(),
					detailPageParser.getClass().getInterfaces(), invocationHandler);
		return proxyDetailPageParser;
	}
}

























