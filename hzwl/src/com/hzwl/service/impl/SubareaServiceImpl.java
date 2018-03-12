package com.hzwl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzwl.dao.ISubareaDao;
import com.hzwl.entity.Subarea;
import com.hzwl.service.ISubareaService;
import com.hzwl.utils.PageBean;

@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService{
	@Resource
	private ISubareaDao subareaDao;

	public void save(Subarea model) {
		subareaDao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
	}

	public List<Subarea> findAll() {
		return subareaDao.findAll();
	}

	@Override
	public void editSubarea(Subarea model) {
		// TODO Auto-generated method stub
		subareaDao.update(model);
	}

	@Override
	public void deleteSubarea(String ids) {
		// TODO Auto-generated method stub
		String[] regionIds = ids.split(",");
		for (String id : regionIds) {
			subareaDao.executeUpdate("subarea.delete", id);
		}
	}

	/**
	 * 查询外键为空的分区
	 */
	@Override
	public List<Subarea> findNotAssociation() {
		// TODO Auto-generated method stub
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByAnyQuery(detachedCriteria);
	}

	/**
	 * 查询已经关联到定区的分区
	 */
	@Override
	public List<Subarea> findAssoctionDecidedzone(String decidedzoneId) {
		// TODO Auto-generated method stub
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.eq("decidedzone", decidedzoneId));
		return subareaDao.findByAnyQuery(detachedCriteria);
	}
}
