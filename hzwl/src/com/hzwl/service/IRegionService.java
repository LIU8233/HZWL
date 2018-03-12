package com.hzwl.service;

import java.util.List;

import com.hzwl.entity.Region;
import com.hzwl.utils.PageBean;

public interface IRegionService {

	public void saveRegion(Region entity);
	
	public void saveBatch(List<Region> list);

	public void pageQuery(PageBean pageBean);

	public List<Region> findAll();

	public List<Region> findByQquery(String query);

	public void deleteMany(String ids);

	public void updataRegion(Region model);

}
