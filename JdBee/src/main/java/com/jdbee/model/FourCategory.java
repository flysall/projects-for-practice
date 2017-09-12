package com.jdbee.model;

import java.util.List;

/**
 * 四级类目
 */
public class FourCategory {
	private String name;
	private String url;

	private List<FiveCategory> fiveCates;// 五级类别

	public List<FiveCategory> getFiveCates() {
		return fiveCates;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public void setFiveCates(List<FiveCategory> fiveCates) {
		this.fiveCates = fiveCates;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "FourCategory [name=" + name + ", url=" + url + ", fiveCates=" + fiveCates + "]";
	}
}
