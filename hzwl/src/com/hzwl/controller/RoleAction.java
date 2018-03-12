package com.hzwl.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.hzwl.controller.base.BaseAction;
import com.hzwl.entity.Role;


@Controller
public class RoleAction extends BaseAction<Role>{
	
	
	private String ids;
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String addRole(){
		
		roleService.save(model, ids);
		
		return "list";
	}
	
	public String delete(){
		roleService.deleteRole(model.getId());
		return "list";
	}
	
	/**
	 * 角色的分页查询
	 * @return
	 * @throws IOException
	 */
	public String pageRole() throws IOException{
		
		roleService.pageQuery(pageBean);
		
		this.writePageBean2Json(pageBean, new String[]{"functions","users","currentPage","detachedCriteria","pageSize"});
		
		return null;
	}
	
	public String listRoleName() throws IOException{
		
		List<Role> list=roleService.findAll();
		String[] excludes=new String[]{"functions","users"};
		this.writeList2Json(list, excludes);
		
		return null;
	}

}
