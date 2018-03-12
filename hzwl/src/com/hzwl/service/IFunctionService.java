package com.hzwl.service;

import java.util.List;

import com.hzwl.entity.Function;
import com.hzwl.utils.PageBean;

public interface IFunctionService {

	//分页
	public void pageQuery(PageBean pageBean);

	//查询全部
	public List<Function> findAll();

	//添加
	public void save(Function model);

	//查找菜单
	public List<Function> findMenu();

	public void deleteFunction(String ids);

}
