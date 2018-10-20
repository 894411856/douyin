package com.openwan.dao;

import com.openwan.model.DouYinUrl;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DouYinDao {
	
	public int insertEmail(DouYinUrl ve);


}
