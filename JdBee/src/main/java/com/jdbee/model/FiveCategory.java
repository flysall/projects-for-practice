package com.jdbee.model;

/**
 * 五级目录
 */
public class FiveCategory {
	private String name;
	private String url;

	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "name=" + name + ", url=" + url;
	}
}
