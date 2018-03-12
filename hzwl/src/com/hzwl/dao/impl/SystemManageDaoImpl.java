package com.hzwl.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hzwl.dao.ISystemManageDao;
import com.hzwl.dao.base.impl.BaseDaoImpl;
import com.hzwl.entity.SystemManage;


@Repository
public class SystemManageDaoImpl extends BaseDaoImpl<SystemManage> implements ISystemManageDao{

	@Override
	public List<SystemManage> findSystemMenu() {
		// TODO Auto-generated method stub
		String hql = "FROM SystemManage s  ORDER BY s.zindex DESC";
		return this.getHibernateTemplate().find(hql);
	}

}
