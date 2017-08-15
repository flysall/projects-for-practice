package com.crawl.proxy.site.mimiip;

import com.crawl.proxy.ProxyListPageParser;
import com.crawl.proxy.entity.Proxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.util.*;

import static com.crawl.core.util.Constants.TIME_INTERVAL;

public class MimiipProxyListPageParser implements ProxyListPageParser{
	@Override
	public List<Proxy> parse(String html){
		Document document = Jsoup.parse(html);
		Elements elements = document.select("table[class=list] tr");
		List<Proxy> proxyList = new ArrayList<>(elements.size());
		for(Element element : elements){
			String ip = element.select("td:eq(0)").first().text();
			String port = element.select("td:eq(1)").first().text();
			String isAnonymous = element.select("td:eq(3)").first().text();
//			if(!anonymousFlag || isAnonymous.contains("匿")){
				proxyList.add(new Proxy(ip, Integer.valueOf(port), TIME_INTERVAL));
//			}
		}
		return proxyList;
	}
}
