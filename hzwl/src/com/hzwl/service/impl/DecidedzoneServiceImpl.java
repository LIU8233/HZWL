package com.hzwl.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzwl.dao.IDecidedzoneDao;
import com.hzwl.dao.ISubareaDao;
import com.hzwl.entity.Decidedzone;
import com.hzwl.entity.Staff;
import com.hzwl.entity.Subarea;
import com.hzwl.service.IDecidedzoneService;
import com.hzwl.utils.PageBean;

@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService{
	
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	
	@Autowired
	private ISubareaDao subareaDao;

	@Override
	public void saveDecidedzone(Decidedzone model, String[] subareaid) {
		// TODO Auto-generated method stub
		decidedzoneDao.save(model);
		for (String id : subareaid) {
			
			Subarea subarea=subareaDao.findById(id);
			subarea.setDecidedzone(model);
		}
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		// TODO Auto-generated method stub
		decidedzoneDao.pageQuery(pageBean);
	}

	@Override
	public void updataDecidedzone(Decidedzone model) {
		// TODO Auto-generated method stub
		decidedzoneDao.update(model);
	}

	@Override
	public void deleteDecidedzone(String ids) {
		// TODO Auto-generated method stub
		String[] decidedzoneIds = ids.split(",");
		for (String id : decidedzoneIds) {
			
			decidedzoneDao.delete(decidedzoneDao.findById(id));
		}
	}

	@Override
	public List<Decidedzone> findDecidedzoneById(String decidedzoneId) {
		// TODO Auto-generated method stub
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Decidedzone.class);
		detachedCriteria.add(Restrictions.eq("id", decidedzoneId));
		return decidedzoneDao.findByAnyQuery(detachedCriteria);
	}

}
