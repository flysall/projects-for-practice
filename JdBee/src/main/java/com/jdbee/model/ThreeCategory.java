package com.jdbee.model;

import java.util.List;

/**
 * 三级类目
 */
public class ThreeCategory {
	private String name;
	private String url;

	private List<FourCategory> fourCates;// 四级类别

	public List<FourCategory> getFourCates() {
		return fourCates;
	}

	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	public void setFourCates(List<FourCategory> fourCates) {
		this.fourCates = fourCates;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ThreeCategory [name=" + name + ", url=" + url + ", fourCates=" + fourCates + "]";
	}
}
