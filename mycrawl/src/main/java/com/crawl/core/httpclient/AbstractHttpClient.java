package com.crawl.core.httpclient;

import com.crawl.zhihu.entity.Page;
import com.crawl.core.util.HttpClientUtil;
import com.crawl.core.util.SimpleLogger;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class AbstractHttpClient {
	private Logger logger = SimpleLogger.getSimpleLogger(AbstractHttpClient.class);
	public InputStream getWebPageInputStream(String url){
		try{
			CloseableHttpResponse response = HttpClientUtil.getResponse(url);
		}
	}
}










































