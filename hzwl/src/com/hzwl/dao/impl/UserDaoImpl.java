package com.hzwl.dao.impl;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hzwl.dao.IUserDao;
import com.hzwl.dao.base.impl.BaseDaoImpl;
import com.hzwl.entity.User;

@Repository
//@Scope("prototype")
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao{
	/**
	 * 根据用户名和密码查询用户
	 */
	public User findByUsernameAndPassword(String username, String password) {
		String hql = "FROM User u WHERE u.username = ? AND u.password = ?";
		List<User> list = this.getHibernateTemplate().find(hql, username,password);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		String hql = "FROM User u WHERE u.username = ?";
		List<User> list = this.getHibernateTemplate().find(hql, username);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
		
	}

}
