package com.jdbee.crawler;

import com.jdbee.main.NewMain;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;

public abstract class RetailersCrawler extends DeepCrawler {
	public final Logger log = Logger.getLogger(NewMain.class);
	private String seedFormat; // 种子初始化
	protected RegexRule regexRule; // 正则匹配

	public RetailersCrawler(String crawlPath, String seedFormat) {
		super(crawlPath);
		this.seedFormat = seedFormat;
		this.regexRule = new RegexRule();
	}

	/**
	 * 添加正则支持
	 */
	public void addRegex(String urlRegex) {
		this.regexRule.addRule(urlRegex);
	}

	/**
	 * 添加一个种子url
	 */
	private void addSeed() throws Exception {
		int totalPage = getTotalPage(getPage(getSeed(seedFormat, 1)));
		for (int page = 1; page <= totalPage; page++) {
			this.addSeed(getSeed(seedFormat, page));
		}
	}

	/**
	 * 根据url获取Page实例
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private Page getPage(String url) throws Exception {
		HttpRequest httpRequest = new HttpRequest(url);
		HttpResponse response = httpRequest.getResponse();
		Page page = new Page();
		page.setUrl(url);
		page.setHtml(response.getHtmlByCharsetDetect());
		page.setResponse(response);
		return page;
	}

	/**
	 * 获取seed url
	 * 
	 * @param seedFomat
	 * @param page
	 * @return
	 */
	public String getSeed(String seedFomat, Object... page) {
		return String.format(seedFormat, page);
	}

	/**
	 * 查询商品总页数
	 */
	public abstract int getTotalPage(Page page);

	@Override
	public void start(int depth) throws Exception {
		addSeed();
		super.start(depth);
	}

	public abstract void visit(Page page, Links links);

	public Links visitAndGetNextLinks(Page page) {
		Links nextLinks = new Links();
		String contentType = page.getResponse().getContentType();
		if (contentType != null && contentType.contains("text/html")) {
			Document doc = page.getDoc();
			if (doc != null) {
				nextLinks.addAllFromDocument(page.getDoc(), regexRule);
			}
		}
		try {
			visit(page, nextLinks);
		} catch (Exception ex) {
			log.info("Exception", ex);
		}
		return nextLinks;
	}

}
