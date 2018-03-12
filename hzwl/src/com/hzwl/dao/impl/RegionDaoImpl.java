package com.hzwl.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hzwl.dao.IRegionDao;
import com.hzwl.dao.base.impl.BaseDaoImpl;
import com.hzwl.entity.Region;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao{

	public List<Region> findByQuery(String query) {
		String hql = "FROM Region WHERE province LIKE ? OR city LIKE ? OR district LIKE ?";
		return this.getHibernateTemplate().find(hql, "%"+query+"%","%"+query+"%","%"+query+"%");
	}

}
