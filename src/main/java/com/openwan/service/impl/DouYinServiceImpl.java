package com.openwan.service.impl;

import javax.annotation.Resource;

import com.openwan.dao.DouYinDao;
import com.openwan.model.DouYinUrl;
import org.springframework.stereotype.Service;

import com.openwan.service.DouYinService;


@Service
public class DouYinServiceImpl implements DouYinService {

	@Resource
	private DouYinDao douYinDao;
	
	public void insertEmailCode(DouYinUrl vec) {
		douYinDao.insertEmail(vec);
	}

}
