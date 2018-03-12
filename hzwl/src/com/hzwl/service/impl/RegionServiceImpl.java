package com.hzwl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzwl.dao.IRegionDao;
import com.hzwl.entity.Region;
import com.hzwl.service.IRegionService;
import com.hzwl.utils.PageBean;

@Service
@Transactional
public class RegionServiceImpl implements IRegionService{
	@Resource
	private IRegionDao regionDao;

	public void saveBatch(List<Region> list) {
		
		for (Region region : list) {
			regionDao.saveOrUpdate(region);
		}
	}

	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
	}

	public List<Region> findAll() {
		return regionDao.findAll();
	}

	/*public List<Region> findByQ(String query) {
	}
*/
	@Override
	public List<Region> findByQquery(String query) {
		// TODO Auto-generated method stub
		return regionDao.findByQuery(query);
	}

	@Override
	public void saveRegion(Region entity) {
		// TODO Auto-generated method stub
		regionDao.save(entity);
	}

	@Override
	public void deleteMany(String ids) {
		// TODO Auto-generated method stub
		String[] regionIds = ids.split(",");
		for (String id : regionIds) {
			
			regionDao.executeUpdate("region.delete", id);
		}
	}

	@Override
	public void updataRegion(Region model) {
		// TODO Auto-generated method stub
		regionDao.update(model);
	}
}
