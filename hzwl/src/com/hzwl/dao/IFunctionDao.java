package com.hzwl.dao;

import java.util.List;

import com.hzwl.dao.base.IBaseDao;
import com.hzwl.entity.Function;

public interface IFunctionDao extends IBaseDao<Function>{

	public List<Function> findListByUserid(String id);

	public List<Function> findAllMenu();

	public List<Function> findMenuByUserid(String id);
	
	

}
