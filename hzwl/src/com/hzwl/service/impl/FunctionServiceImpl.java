package com.hzwl.service.impl;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzwl.dao.IFunctionDao;
import com.hzwl.entity.Function;
import com.hzwl.entity.User;
import com.hzwl.service.IFunctionService;
import com.hzwl.utils.PageBean;

@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {
	@Autowired
	private IFunctionDao functionDao;

	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
	}

	public List<Function> findAll() {
		return functionDao.findAll();
	}

	public void save(Function model) {
		
		Function function = model.getFunction();
		if (function != null && function.getId().equals("")) {
			model.setFunction(null);
		}
		functionDao.save(model);
	}

	/**
	 * 查询菜单
	 */
	public List<Function> findMenu() {
		User user =(User) ServletActionContext.getRequest().getSession()
				.getAttribute("loginUser");
		List<Function> list = null;
		if (user.getUsername().equals("admin")) {
			//查询所有菜单
			list = functionDao.findAllMenu();
		}else{
			//普通用户，查询对应的菜单
			list = functionDao.findMenuByUserid(user.getId());
		}
		return list;
	}

	@Override
	public void deleteFunction(String ids) {
		// TODO Auto-generated method stub
		String []functionIds=ids.split(",");
		for (String id : functionIds) {
			functionDao.delete(functionDao.findById(id));
		}
	}
}
