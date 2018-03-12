package com.hzwl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzwl.dao.ICarDao;
import com.hzwl.entity.Car;
import com.hzwl.service.ICarService;
import com.hzwl.utils.PageBean;

@Service
@Transactional
public class CarServiceImpl implements ICarService{
	
	@Autowired
	private ICarDao cardao;

	@Override
	public void pageQuery(PageBean pageBean) {
		// TODO Auto-generated method stub
		cardao.pageQuery(pageBean);
	}

	@Override
	public void saveCar(Car model) {
		// TODO Auto-generated method stub
		cardao.save(model);
	}

	@Override
	public void deleteCar(String ids) {
		// TODO Auto-generated method stub
		String [] carids=ids.split(",");
		for (String id : carids) {
			cardao.executeUpdate("car.delete", Integer.parseInt(id));
		}
	}

	@Override
	public void updataCar(Car model) {
		// TODO Auto-generated method stub
		cardao.update(model);
	}

}
