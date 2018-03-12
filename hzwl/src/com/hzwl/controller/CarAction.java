package com.hzwl.controller;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;

import com.hzwl.controller.base.BaseAction;
import com.hzwl.entity.Car;

@Controller
public class CarAction extends BaseAction<Car>{
	
	
	private String ids;
	
	
	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public String editCar(){
		carService.updataCar(model);
		return "list";
	}

	/**
	 * 删除车辆
	 * @return
	 */
	public String delete(){
		carService.deleteCar(ids);
		return null;
	}

	/**
	 * 增加车辆
	 * @return
	 */
	public String addcar(){
		carService.saveCar(model);
		return "list";
	}
	
	
	public String pageQuery() throws IOException{
		
		// 在查询之前，封装条件
		DetachedCriteria detachedCriteria2 = pageBean.getDetachedCriteria();
		String querytype = model.getCartype();
		String querynumber = model.getCarnumber();
		String queryname = model.getCarname();
		String querydriver = model.getDriver();

		// 进行模糊查询
		if (StringUtils.isNotBlank(querytype)) {
			detachedCriteria2.add(Restrictions.like("cartype", "%" + querytype
					+ "%"));
		}
		if (StringUtils.isNotBlank(querynumber)) {
			detachedCriteria2.add(Restrictions.like("carnumber", "%" + querynumber
					+ "%"));
		}
		if (StringUtils.isNotBlank(queryname)) {
			detachedCriteria2.add(Restrictions.like("carname", "%" + queryname
					+ "%"));
		}
		if (StringUtils.isNotBlank(querydriver)) {
			detachedCriteria2.add(Restrictions.like("driver", "%" + querydriver
					+ "%"));
		}
		
		carService.pageQuery(pageBean);
		
		this.writePageBean2Json(pageBean, new String[]{"currentPage",
				"detachedCriteria", "pageSize"});
		
		return null;
	}

}
