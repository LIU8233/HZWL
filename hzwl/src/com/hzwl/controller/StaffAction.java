package com.hzwl.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hzwl.controller.base.BaseAction;
import com.hzwl.entity.Staff;

/**
 * 取派员管理
 * 
 * 
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
	
	
	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 添加取派员
	 */
	public String addStaff() {
		
		System.out.println(model);
		
		staffService.save(model);
		return "list";
	}

	/**
	 * 分页查询方法
	 * 
	 * @throws IOException
	 */
	public String pageQuery() throws IOException {

		// 在查询之前，封装条件
		DetachedCriteria detachedCriteria2 = pageBean.getDetachedCriteria();
		String queryname = model.getName();
		String querytell = model.getTelephone();
		String querystation = model.getStation();

		// 进行模糊查询
		if (StringUtils.isNotBlank(queryname)) {
			detachedCriteria2.add(Restrictions.like("name", "%" + queryname
					+ "%"));
		}
		if (StringUtils.isNotBlank(querytell)) {
			detachedCriteria2.add(Restrictions.like("telephone", "%" + querytell
					+ "%"));
		}
		if (StringUtils.isNotBlank(querystation)) {
			detachedCriteria2.add(Restrictions.like("station", "%" + querystation
					+ "%"));
		}

		staffService.pageQuery(pageBean);
		
		this.writePageBean2Json(pageBean, new String[] { "currentPage",
				"detachedCriteria", "pageSize","decidedzones" });
		
		return NONE;
	}


	

	/**
	 * 删除功能
	 * 
	 * @return
	 */
	
	public String deleteStaff() {
		
		staffService.deleteBatch(ids);
		return "list";
	}

	/**
	 * 修改取派员信息
	 */
	public String editStaff() {
		
		// 显查询数据库中原始数据
		Staff staff = staffService.findById(model.getId());

		// 再按照页面提交的参数进行覆盖
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setStation(model.getStation());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());

		staffService.update(staff);
		
		return "list";
	}
	
	public String listStaff() throws IOException{
		
		List<Staff> list=staffService.findAll();
		String[] excludes = new String[]{"decidedzones"};
		this.writeList2Json(list, excludes);
		
		return null;
	}
}
