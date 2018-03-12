package com.hzwl.dao;

import java.util.List;

import com.hzwl.dao.base.IBaseDao;
import com.hzwl.entity.SystemManage;

public interface ISystemManageDao extends IBaseDao<SystemManage>{
	
	public List<SystemManage> findSystemMenu();

}
