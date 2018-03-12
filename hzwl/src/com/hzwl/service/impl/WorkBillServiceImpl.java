package com.hzwl.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzwl.dao.IWorkbillDao;
import com.hzwl.service.IWorkBillService;
import com.hzwl.utils.PageBean;

@Service
@Transactional
public class WorkBillServiceImpl implements IWorkBillService{

	@Resource
	private IWorkbillDao workDao;
	
	@Override
	public void pageList(PageBean pageBean) {
		// TODO Auto-generated method stub
		workDao.pageQuery(pageBean);
	}

}
