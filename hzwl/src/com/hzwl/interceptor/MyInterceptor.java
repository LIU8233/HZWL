package com.hzwl.interceptor;

import org.apache.struts2.ServletActionContext;

import com.hzwl.entity.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class MyInterceptor extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("loginUser");
		if(user == null){
			//未登录，跳转到登录页面
			return "login";
		}
		return arg0.invoke();// 放行
	}

}
