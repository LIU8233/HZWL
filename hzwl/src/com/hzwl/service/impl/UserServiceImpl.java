package com.hzwl.service.impl;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzwl.dao.IRoleDao;
import com.hzwl.dao.IUserDao;
import com.hzwl.entity.Role;
import com.hzwl.entity.User;
import com.hzwl.service.IUserService;
import com.hzwl.utils.PageBean;

@Service
@Transactional
public class UserServiceImpl implements IUserService{
	//注入dao
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IdentityService identityService;

	public User login(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		return userDao.findByUsernameAndPassword(username,password);
	}

	public void editPassword(String password, String id) {
		userDao.executeUpdate("editPassword", password,id);
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		// TODO Auto-generated method stub
		userDao.pageQuery(pageBean);
	}

	@Override
	public void save(User user, String[] roleIds) {
		// TODO Auto-generated method stub
		
		userDao.save(user);
		/**
		 * 同步用户到工作流中去
		 */
		org.activiti.engine.identity.User actUser=new UserEntity(user.getId());
		identityService.saveUser(actUser);
		
		for (String roleId : roleIds) {
			
			Role role = roleDao.findById(roleId);
			user.getRoles().add(role);
			
			identityService.createMembership(actUser.getId(), role.getName());
		}
	}

	@Override
	public void deleteUser(String ids) {
		// TODO Auto-generated method stub
		String[] userIds=ids.split(",");
		for (String id : userIds) {
			userDao.delete(userDao.findById(id));
		}
	}

	
}
