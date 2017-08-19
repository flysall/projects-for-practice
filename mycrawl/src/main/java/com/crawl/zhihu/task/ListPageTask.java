package com.crawl.zhihu.task;

import com.crawl.core.util.Config;
import com.crawl.core.util.Constants;
import com.crawl.zhihu.dao.ZhiHuDAO;
import com.crawl.zhihu.entity.Page;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.List;

/**
 * 知乎用户关注列表页task
 * 下载成功解析出用户token，构造用户详情url，添加到DetailPageDownloadThreadPool
 */
public class ListPageTask extends AbstractPageTask{
	public ListPageTask(HttpRequestBase request, boolean proxyFlag){
		super(request, proxyFlag);
	}
	
	@Override
	void retry(){
		zhiHuHttpClient.getListPageThreadPool().execute(new ListPageTask(request, Config.isProxy));
	}
	
	@Override
	void handle(Page page){
		//我关注的人的列表页
		List<String> urlTokenList = JsonPath.parse(page.getHtml()).read("$.data..url_token");
		for(String s : urlTokenList){
			if(s == null){
				continue;
			}
			handleUserToken(s);
		}
	}
	
	private void handleUserToken(String userToken){
		
	}
}
























