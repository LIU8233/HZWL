package com.hzwl.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.hzwl.controller.base.BaseAction;
import com.hzwl.entity.Function;


@Controller
public class FunctionAction extends BaseAction<Function>{
	
	
	private String ids;
	
	
	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}


	/**
	 * 添加权限
	 * @return
	 */
	public String addFunction(){
		
		functionService.save(model);
		return "list";
	}
	
	
	public String delete(){
		functionService.deleteFunction(ids);
		return "list";
	}
	
	/**
	 * 分页查询
	 * @return
	 * @throws IOException
	 */
	public String pageQuery() throws IOException{
		
		
		String page = model.getPage();
		pageBean.setCurrentPage((Integer.parseInt(page)));
		functionService.pageQuery(pageBean);
		String[] excludes=new String[]{"currentPage","detachedCriteria","pageSize","function","functions","roles"};
		this.writePageBean2Json(pageBean, excludes);
		return null;
	}
	
	
	public String findAll() throws IOException{
		List<Function> list=functionService.findAll();
		this.writeList2Json(list, new String[]{"function","functions","roles"});
		return null;
	}
	
	/**
	 * 加载左侧菜单
	 * @return
	 * @throws IOException
	 */
	public String loadMenu() throws IOException{
		
		List<Function> list=functionService.findMenu();
		
		String[] excludes=new String[]{"functions","function","roles"};
		
		this.writeList2Json(list, excludes);
		
		return null;
	}
	
	

}
