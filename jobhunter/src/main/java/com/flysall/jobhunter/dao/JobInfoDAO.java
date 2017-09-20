package com.flysall.jobhunter.dao;

import org.apache.ibatis.annotations.Insert;
import com.flysall.jobhunter.model.LieTouJobInfo;

public interface JobInfoDAO {
	@Insert("insert into JobInfo ('title', 'salary', 'company', 'description', 'source', 'url', 'urlMd5') values (#{title}, #{salary}, #{company}, #{description}, #{source}, #{url}, #{urlMd5})")
	public int add(LieTouJobInfo jobInfo);
}
