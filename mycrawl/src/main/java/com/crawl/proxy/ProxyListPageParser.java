package com.crawl.proxy;

import com.crawl.core.parser.Parser;
import com.crawl.proxy.entity.Proxy;
import com.crawl.zhihu.entity.Page;

import java.util.List;

public interface ProxyListPageParser extends Parser {
	/**
	 * 是否只需要是否匿名代理
	 */
	static final boolean anonymousFlag = true;
	List<Proxy> parse(String content);
}
