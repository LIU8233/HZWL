package com.hzwl.dao;

import java.util.List;

import com.hzwl.dao.base.IBaseDao;
import com.hzwl.entity.Region;

public interface IRegionDao extends IBaseDao<Region> {

	public List<Region> findByQuery(String query);
}
