package com.hzwl.service;

import com.hzwl.entity.User;
import com.hzwl.utils.PageBean;

public interface IUserService {

	public User login(User model);

	public void editPassword(String password, String id);

	public void pageQuery(PageBean pageBean);

	public void save(User user, String[] roleIds);

	public void deleteUser(String ids);
}
