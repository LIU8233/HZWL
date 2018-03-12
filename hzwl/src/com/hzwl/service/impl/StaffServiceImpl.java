package com.hzwl.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzwl.dao.IStaffDao;
import com.hzwl.entity.Staff;
import com.hzwl.service.IStaffService;
import com.hzwl.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService{
	//注入dao
	@Autowired
	private IStaffDao staffDao;

	public void save(Staff model) {
		
		staffDao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}

	/**
	 * 批量删除
	 */
	public void deleteBatch(String ids) {
		
		String[] staffIds = ids.split(",");
		for (String id : staffIds) {
			staffDao.executeUpdate("staff.delete", id);
		}
	}

	public Staff findById(String id) {
		return staffDao.findById(id);
	}
	
	public void update(Staff staff) {
		staffDao.update(staff);
	}

	@Override
	public List<Staff> findAll() {
		// TODO Auto-generated method stub
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Staff.class);
		/**
		 * 如果有条件的就直接添加进去
		 * detachedCriteria.add(Restrictions.eq(propertyName, value))
		 */
		return staffDao.findByAnyQuery(detachedCriteria);
	}
}
