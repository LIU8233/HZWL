package com.hzwl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzwl.dao.ISystemManageDao;
import com.hzwl.entity.SystemManage;
import com.hzwl.service.ISystemManageService;

@Service
@Transactional
public class SystemManageServiceImpl implements ISystemManageService{
	
	@Autowired
	private ISystemManageDao systemDao;

	@Override
	public List<SystemManage> findSystemMenu() {
		// TODO Auto-generated method stub
		return systemDao.findSystemMenu();
	}

}
