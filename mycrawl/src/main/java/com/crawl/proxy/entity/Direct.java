package com.crawl.proxy.entity;

/**
 * 不使用代理， 直接连接
 */
public class Direct extends Proxy{
	public Direct(String ip, int port, long delayTime){
		super(ip, port, delayTime);
	}
	public Direct(long delayTime){
		this("", 0, delayTime);
	}
}
