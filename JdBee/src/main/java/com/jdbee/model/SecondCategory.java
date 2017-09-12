package com.jdbee.model;

import java.util.List;

public class SecondCategory {
	private String name;
	private String url;

	private List<ThreeCategory> threeCates;// 三级类别

	public String getName() {
		return name;
	}

	public List<ThreeCategory> getThreeCates() {
		return threeCates;
	}

	public String getUrl() {
		return url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setThreeCates(List<ThreeCategory> threeCates) {
		this.threeCates = threeCates;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "name=" + name + ", url=" + url + ", threeCates=" + threeCates;
	}
}
