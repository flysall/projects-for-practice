package com.jdbee.crawler;

import com.jdbee.dao.GoodsDao;
import com.jdbee.model.Goods;
import com.jdbee.utils.Constants;
import com.jdbee.utils.PageUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import cn.edu.hfut.dmic.webcollector.model.Page;

public class JDGoodsList {
	private static final long serialVersionUID = -6016161025701938903L;

	private static GoodsDao goodsDao;

	static {
		ApplicationContext context = new ClassPathXmlApplicationContext("springJdbcContext.xml");
		goodsDao = (GoodsDao) context.getBean("goodsDao");
	}

	public final Logger log = Logger.getLogger(JDGoodsList.class);
	
	/**
	 * 解析Page， 将信息存入Goods对象中
	 * @param page
	 */
	public void addGoods(Page page) {
		WebDriver driver = null;
		try {
			driver = PageUtils.getWebDriver(page);
			System.out.println("爬取地址: " + page.getUrl());
			List<WebElement> eles = driver.findElements(By.cssSelector("li.gl-item"));
			if (eles != null) {
				for (WebElement ele : eles) {
					Goods g = new Goods();
					g.setPlatform(Constants.JD);
					String priceStr = ele.findElement(By.className("p-price")).findElement(By.className("J-price"))
							.findElement(By.tagName("i")).getText();
					if (!StringUtils.isBlank(priceStr) && !"null".equals(priceStr))
						g.setPrice(priceStr);
					else
						g.setPrice("-1");
					g.setName(ele.findElement(By.className("p-name")).findElement(By.tagName("em")).getText());
					g.setUrl(ele.findElement(By.className("p-name")).findElement(By.tagName("a")).getAttribute("href"));
					String commitCnt = ele.findElement(By.className("p-commit")).findElement(By.tagName("a")).getText();
					if (!StringUtils.isBlank(commitCnt) && !"null".equals(commitCnt)) {
						g.setCommitCnt(commitCnt);
					} else {
						g.setCommitCnt("-1");
					}
					System.out.println(g.toString());
					goodsDao.createGoods(g);
				}
			} else {
				log.info("无商品列表");
			}
		} catch (Exception e) {
			log.warn("爬取异常");
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}
}
