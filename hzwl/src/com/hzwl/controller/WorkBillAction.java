package com.hzwl.controller;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;

import com.hzwl.controller.base.BaseAction;
import com.hzwl.entity.Staff;
import com.hzwl.entity.Workbill;

@Controller
public class WorkBillAction extends BaseAction<Workbill> {
	

	public String pageListWorkBill() throws IOException{
		
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		
		Staff staff=model.getStaff();
		
		if(staff!=null){
			detachedCriteria.createAlias("staff", "s");
			
			String queryName=staff.getStation();
			
			if (StringUtils.isNotBlank(queryName)) {
				// 按照省进行模糊查询
				detachedCriteria.add(Restrictions.like("s.station", "%"
						+ queryName + "%"));
			}
		}
		
		workbillService.pageList(pageBean);
		
		String[] excludes=new String[]{"currentPage",
				"detachedCriteria", "pageSize","noticebill","staff"};
		
		this.writePageBean2Json(pageBean, excludes);
		
		return null;
	}
}
