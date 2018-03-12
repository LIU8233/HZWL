package com.hzwl.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hzwl.entity.User;
import com.hzwl.entity.Workordermanage;
import com.hzwl.service.IWorkordermanageService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 任务管理
 * @author Administrator
 *
 */
@Controller
public class TaskAction extends ActionSupport{

	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IWorkordermanageService  workordermanageService;
	
	private Integer check;
	
	
	public Integer getCheck() {
		return check;
	}


	public void setCheck(Integer check) {
		this.check = check;
	}

	private String taskId;
	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	/**
	 * 展示组所有的任务
	 * @return
	 */
	public String listGroupTask(){
		
		TaskQuery taskQuery = taskService.createTaskQuery();
		
		User user = (User) ServletActionContext.getRequest().getSession()
		.getAttribute("loginUser");
		
		String taskUser=user.getId();
		
		taskQuery.taskCandidateUser(taskUser);//组任务过滤
		
		List<Task> list = taskQuery.list();
		
		ActionContext.getContext().getValueStack().set("list", list);
		
		return "list";
	}
	
	
	public String showData() throws IOException {
		
		Map<String, Object> variables = taskService.getVariables(taskId);
		
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		
		ServletActionContext.getResponse().getWriter()
				.print(variables.toString());
		
		return null;
	}
	
	public String takeTask(){
		
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("loginUser");
				
		String userId=user.getId();
		
		taskService.claim(taskId, userId);
		
		return "toList";
	}
	
	public String findPersonTask(){
		
		TaskQuery taskQuery = taskService.createTaskQuery();
		
		User user = (User) ServletActionContext.getRequest().getSession()
		.getAttribute("loginUser");
		
		String assignee=user.getId();
		
		taskQuery.taskAssignee(assignee);//个人任务过滤
		
		List<Task> list = taskQuery.list();
		
		ActionContext.getContext().getValueStack().set("list", list);
		
		return "persontasklist";
	}
	
	/**
	 * 办理审核工作单任务
	 */
	public String checkWork() {
		
		// 根据任务id查询任务对象
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		// 根据任务对象查询流程实例id
		String processInstanceId = task.getProcessInstanceId();
		
		// 根据流程实例id查询流程实例对象
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		
		String workordermanageId = processInstance.getBusinessKey();
		
		Workordermanage workordermanage = workordermanageService.findById(workordermanageId);
		
		if(check == null){
			//跳转到审核页面
			// 跳转到一个审核工作单页面，展示当前对应的工作单信息
			ActionContext.getContext().getValueStack().set("map", workordermanage);
			return "check";
			
		}else{
			
			workordermanageService.checkWorkordermanage(taskId,check,workordermanageId);
			return "topersonaltasklist";
		}
	}
	
	public String goodsOutStorage(){
		taskService.complete(taskId);
		return "topersonaltasklist";
	}
	
	
	public String goodsDelivery(){
		taskService.complete(taskId);
		return "topersonaltasklist";
	}
	
	public String receive(){
		taskService.complete(taskId);
		return "topersonaltasklist";
	}


}
