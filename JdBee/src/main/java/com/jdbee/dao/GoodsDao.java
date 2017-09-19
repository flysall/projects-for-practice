package com.jdbee.dao;

import com.jdbee.model.Goods;
import org.springframework.stereotype.Service;

/**
 * 有关货物的数据库
 */
public class GoodsDao extends BaseDao{
	/**
	 * 对数据库进行数据插入
	 * @param goods 货物
	 */
	public void createGoods(Goods goods){
		String sql = "INSERT INTO GOODS(PLATFORM, URL, PRICE, COMMITCNT) values(?,?,?,?,?)";
		jdbcTemplate.update(sql, goods.getPlatform(), goods.getUrl(), goods.getName(), goods.getPrice(), goods.getCommitCnt());
	}
	/**
	 * 根据id获取货物的名字
	 * @param id
	 * @return
	 */
	public String getGoodsName(Long id){
		String sql = "SELECT NAME FROM GOODS WHERE ID=?";
		return jdbcTemplate.queryForObject(sql, String.class,id);
	}
}
