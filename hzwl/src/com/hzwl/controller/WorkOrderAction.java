package com.hzwl.controller;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.hzwl.controller.base.BaseAction;
import com.hzwl.entity.Workordermanage;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class WorkOrderAction extends BaseAction<Workordermanage>{
	
	public String saveWorkOrder() throws IOException{
		
		String flag = "1";
		try{
			workService.save(model);
		}catch (Exception e) {
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		
		return null;
	}
	
	/**
	 * 展示工作单
	 * @return
	 */
	public String list(){
		
		List<Workordermanage> list=workService.findNoStart();
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	
	/**
	 * 启动流程
	 * @return
	 */
	public String start(){
		
		String id=model.getId();
		workService.start(id);
		return "toList";
	}
	
	

}
