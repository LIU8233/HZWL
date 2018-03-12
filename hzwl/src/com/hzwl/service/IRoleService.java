package com.hzwl.service;

import java.util.List;

import com.hzwl.entity.Role;
import com.hzwl.utils.PageBean;

public interface IRoleService {

	//添加
	public void save(Role model, String ids);

	//分页
	public void pageQuery(PageBean pageBean);

	//查询全部
	public List<Role> findAll();

	public void deleteRole(String id);

}
