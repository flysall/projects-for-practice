package com.crawl.core.parser;

import com.crawl.zhihu.entity.Page;

import java.util.*;

public interface ListPageParser extends Parser{
	List parserListPage(Page page);
}
