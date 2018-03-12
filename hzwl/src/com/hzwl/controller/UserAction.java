package com.hzwl.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hzwl.controller.base.BaseAction;
import com.hzwl.entity.User;
import com.hzwl.service.IUserService;
import com.hzwl.service.impl.UserServiceImpl;
import com.hzwl.utils.MD5Utils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	@Autowired
	private IUserService userService;

	// 通过属性驱动接收验证码
	private String checkcode;
	
	private String[] roleIds;
	
	private String ids;
	

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public String login() throws IOException {
		
		System.out.println("用户------------------："+model);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		// 生成的验证码
		String key = (String) ServletActionContext.getRequest().getSession()
				.getAttribute("key");

		// 判断用户输入的验证码是否正确
		if (StringUtils.isNotBlank(checkcode) && checkcode.equals(key)) {
			// 验证码正确
			// 获得当前用户对象
			Subject subject = SecurityUtils.getSubject();// 状态为“未认证”
			String password = model.getPassword();
			// 构造一个用户名密码令牌
			AuthenticationToken token = new UsernamePasswordToken(
					model.getUsername(), password);
			try {
				subject.login(token);
			} catch (UnknownAccountException e) {
				e.printStackTrace();
				// 设置错误信息
				//this.addActionError(this.getText("usernamenotfound"));
				out.print("3");
				return null;
				//return "login";
			} catch (Exception e) {
				e.printStackTrace();
				// 设置错误信息
				out.print("1");
				return null;
				//return "login";
			}
			// 获取认证信息对象中存储的User对象
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession()
					.setAttribute("loginUser", user);
			ServletActionContext.getRequest().getSession()
			.setAttribute("username", user.getUsername());
			out.print("2");
			return null;
			//return "home";
		} else {
			// 验证码错误,设置错误提示信息，跳转到登录页面
			out.print("0");
			return null;
			//return "login";
		}
	}
	
	public String addUser(){
		
		System.out.println(model);
		
		userService.save(model, roleIds);
		
		return "list";
	}
	
	public String pageQueryUser() throws IOException{
		
		userService.pageQuery(pageBean);
		String[] excludes = new String[]{"noticebills","roles"};
		this.writePageBean2Json(pageBean, excludes);
		
		return null;
	}
	
	public String delete(){
		userService.deleteUser(ids);
		return "list";
	}

	public String loginBack() throws IOException {
		
		ValueStack valueStack = ActionContext.getContext().getValueStack();

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String key = (String) ServletActionContext.getRequest().getSession()
				.getAttribute("key");

		if (key.equalsIgnoreCase(checkcode)) {
			User u1 = userService.login(model);
			if (u1 != null) {

				System.out.println(model.getUsername() + model.getPassword()
						+ "-----------" + checkcode);

				// 保存用户名
				ServletActionContext.getRequest().getSession()
						.setAttribute("username", model.getUsername());

				ServletActionContext.getRequest().getSession()
						.setAttribute("loginUser", u1);

				out.print("2");
				return null;
			} else {
				out.print("1");
				return null;
			}
		} else {
			out.print("0");
			return null;
		}
	}

	/**
	 * 用户退出
	 */
	public String logout() {
		// 销毁session
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}

	/**
	 * 修改当前登录用户密码
	 * 
	 * @throws IOException
	 */
	public String editPassWord() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		String password = model.getPassword();// 新密码
		String flag = "1";
		try {
			userService.editPassword(password, user.getId());
			// db.editPassword(password, user.getId());
			session.invalidate();
		} catch (Exception e) {

			flag = "0";
		}
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
}
