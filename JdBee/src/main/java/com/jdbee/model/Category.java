package com.jdbee.model;

import java.util.List;

/**
 * Category: 一级类目
 */
public class Category {
	private Integer id;
	private String name;
	
	private List<SecondCategory> secondCates; //二级目录
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<SecondCategory> getSecondCates() {
		return secondCates;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSenondCates(List<SecondCategory> senondCates) {
		this.secondCates = senondCates;
	}
	
	@Override 
	public String toString(){
		return "Category [id=" + id + ", name=" + name + ", secondCates=" + secondCates + "]";
	}
}





















