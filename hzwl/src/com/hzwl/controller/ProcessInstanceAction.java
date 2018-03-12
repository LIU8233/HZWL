package com.hzwl.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ProcessInstanceAction extends ActionSupport {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;

	private String id;
	private String deploymentId;
	private String imageName;

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 查询流程实例
	 * 
	 * @return
	 */
	public String listProcessInstance() {

		ProcessInstanceQuery query = runtimeService
				.createProcessInstanceQuery();
		query.orderByProcessInstanceId().desc();
		List<ProcessInstance> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);// 压栈

		return "list";
	}

	/**
	 * 根据流程实例的id查询流程变量数据
	 * 
	 * @return
	 * @throws IOException
	 */
	public String findData() throws IOException {
		Map<String, Object> variables = runtimeService.getVariables(id);
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter()
				.print(variables.toString());
		return null;
	}

	/**
	 * 显示图片
	 * @return
	 */
	public String showImage() {

		// 根据id查询到流程对象
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery().processInstanceId(id)
				.singleResult();

		// 2、根据流程实例对象查询流程定义id
		String processDefinitionId = processInstance.getProcessDefinitionId();
		// 3、根据流程定义id查询流程定义对象
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		// 4、根据流程定义对象查询部署id

		deploymentId = processDefinition.getDeploymentId();
		imageName = processDefinition.getDiagramResourceName();

		// 查询坐标
		// 1、获得当前流程实例执行到哪个节点
		String activityId = processInstance.getActivityId();// usertask1
		// 2、加载bpmn（xml）文件，获得一个流程定义对象
		ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);// 查询act_ge_bytearray
		// 3、根据activitiId获取含有坐标信息的对象
		ActivityImpl findActivity = pd.findActivity(activityId);
		int x = findActivity.getX();
		int y = findActivity.getY();
		int width = findActivity.getWidth();
		int height = findActivity.getHeight();

		ActionContext.getContext().getValueStack().set("x", x);
		ActionContext.getContext().getValueStack().set("y", y);
		ActionContext.getContext().getValueStack().set("width", width);
		ActionContext.getContext().getValueStack().set("height", height);

		return "showImage";
	}
	
	/**
	 * 获取图片流
	 * @return
	 */
	public String imageView(){
		
		InputStream imageStream = repositoryService.getResourceAsStream(deploymentId, imageName);
		ActionContext.getContext().getValueStack().set("imageStream", imageStream);
		return "imageView";
	}
}
