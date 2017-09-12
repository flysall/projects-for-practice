package com.jdbee.model;

public class Goods {
	private String platform; //平台
	private String url; //请求路径
	private String name; //名称
	private String price; //价格
	private String commitCnt; //评论数量
	
	public String getCommitCnt(){
		return commitCnt;
	}
	public String getName() {
		return name;
	}
	// private String img1;
	// private String img2;
	// private String img3;
	// private String img4;
	// private String img5;
	//
	// private String skuId;
	// private String venderId;
	// private String shopId;
	// private String plusPrice;// 会员价
	public String getPlatform() {
		return platform;
	}

	public String getPrice() {
		return price;
	}

	public String getUrl() {
		return url;
	}
	public void setCommitCnt(String commitCnt) {
		this.commitCnt = commitCnt;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override 
	public String toString(){
		return "Goods [platform=" + platform + ", url=" + url + ", name=" + name + ", price=" + price + ", commitCnt=" + commitCnt + "]";
	}
}


















