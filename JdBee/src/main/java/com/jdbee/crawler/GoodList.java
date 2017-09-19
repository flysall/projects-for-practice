package com.jdbee.crawler;

import com.jdbee.model.Goods;

import java.util.ArrayList;

import cn.edu.hfut.dmic.webcollector.model.Page;

/**
 * 抽象类，便于扩展其他平台
 */
public abstract class GoodList extends ArrayList<Goods>{
	private static final long serialVersionUID = -7894645047969514212L;
	
	public abstract void addGoods(Page page);
}
