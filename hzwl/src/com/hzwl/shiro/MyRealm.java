package com.hzwl.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzwl.dao.IFunctionDao;
import com.hzwl.dao.IUserDao;
import com.hzwl.entity.Function;
import com.hzwl.entity.User;

public class MyRealm extends AuthorizingRealm {

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IFunctionDao functionDao;

	/**
	 * 认证方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		System.out.println("认证方法。。。。。。。。。。。。。。。。。。。");
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();// 从令牌中获得用户名

		User user = userDao.findUserByUsername(username);
		System.out.println("myRealm查询到的用户：" + user);
		if (user == null) {
			return null;
		} else {
			String password = user.getPassword();// 获得数据库中存储的密码
			// 创建简单认证信息对象
			/***
			 * 参数一：签名，程序可以在任意位置获取当前放入的对象 参数二：从数据库中查询出的密码 参数三：当前realm的名称
			 */
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
					password, this.getClass().getSimpleName());
			return info;// 返回给安全管理器，由安全管理器负责比对数据库中查询出的密码和页面提交的密码
		}
	}

	/**
	 * 权限授权方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// TODO Auto-generated method stub
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 根据当前登录用户查询其对应的权限，授权
		User user = (User) principals.getPrimaryPrincipal();
		List<Function> list = null;
		if (user.getUsername().equals("admin")) {
			// 当前用户是超级管理员，查询所有权限，为用户授权
			list = functionDao.findAll();
		} else {
			// 普通用户，根据用户id查询对应的权限
			list = functionDao.findListByUserid(user.getId());
		}
		for (Function function : list) {
			info.addStringPermission(function.getCode());
		}
		return info;
	}

}
