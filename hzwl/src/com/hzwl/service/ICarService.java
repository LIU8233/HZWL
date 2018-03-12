package com.hzwl.service;

import com.hzwl.entity.Car;
import com.hzwl.utils.PageBean;

public interface ICarService {

	public void pageQuery(PageBean pageBean);

	public void saveCar(Car model);

	public void deleteCar(String ids);

	public void updataCar(Car model);
}
