package com.jdbee.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.edu.hfut.dmic.webcollector.model.Page;

public class PageUtils {
	/**
	 * 获取webcollector 自带htmlUnitDriver实例
	 * 
	 * @param page
	 * @param browserVersion
	 *            模拟浏览器
	 */
	public static HtmlUnitDriver getDriver(Page page, BrowserVersion browserVersion) {
		HtmlUnitDriver driver = new HtmlUnitDriver(browserVersion);
		driver.setJavascriptEnabled(true);
		driver.get(page.getUrl());
		return driver;
	}

	/**
	 * 获取PhantomJsDriver(可以爬取js动态生成的html)
	 * 
	 * @param page
	 * @return
	 */
	public static WebDriver getWebDriver(Page page) {
		System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				PropertiesUtils.getProperty(PropertiesUtils.PHANTOMJS_DRIVER_PATH));
		WebDriver driver = new PhantomJSDriver();
		driver.get(page.getUrl());
		return driver;
	}

	/**
	 * 根据url获取html
	 * 
	 * @param url
	 */
	public static WebDriver getWebDriver(String url) {
		System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				PropertiesUtils.getProperty(PropertiesUtils.PHANTOMJS_DRIVER_PATH));
		WebDriver driver = new PhantomJSDriver();
		driver.get(url);
		return driver;
	}

	public static void main(String[] args) {
		System.out.println(PropertiesUtils.getProperty(PropertiesUtils.PHANTOMJS_DRIVER_PATH));
	}
}
