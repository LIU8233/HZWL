package com.hzwl.dao;

import com.hzwl.dao.base.IBaseDao;
import com.hzwl.entity.User;

public interface IUserDao extends IBaseDao<User>{
	
	public User findByUsernameAndPassword(String username, String password);

	public User findUserByUsername(String username);

	
}
