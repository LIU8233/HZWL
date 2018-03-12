package com.hzwl.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzwl.dao.IWorkordermanageDao;
import com.hzwl.entity.Workordermanage;
import com.hzwl.service.IWorkordermanageService;

@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService{
	@Autowired
	private IWorkordermanageDao workordermanageDao;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	
	public void save(Workordermanage model) {
		
		model.setUpdatetime(new Date());
		workordermanageDao.save(model);
	}

	@Override
	public List<Workordermanage> findNoStart() {
		// TODO Auto-generated method stub
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Workordermanage.class);
		detachedCriteria.add(Restrictions.eq("start", "0"));
		return workordermanageDao.findByAnyQuery(detachedCriteria);
	}

	@Override
	public void start(String id) {
		// TODO Auto-generated method stub
		Workordermanage workordermanage = workordermanageDao.findById(id);
		workordermanage.setStart("1");//重新设置start值
		
		String processKey="transfer";
		String businessKey=id;
		Map<String, Object> variables=new HashMap<String, Object>();
		variables.put("业务数据",workordermanage);
		runtimeService.startProcessInstanceByKey(processKey,businessKey,variables);
	}

	@Override
	public Workordermanage findById(String workordermanageId) {
		// TODO Auto-generated method stub
		return workordermanageDao.findById(workordermanageId);
	}

	@Override
	public void checkWorkordermanage(String taskId, Integer check,
			String workordermanageId) {
		// TODO Auto-generated method stub
		
		Workordermanage workordermanage = workordermanageDao.findById(workordermanageId);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		Map<String, Object> variables=new HashMap<String, Object>();
		
		variables.put("check", check);
		
		String processInstanceId=task.getProcessInstanceId();
		
		taskService.complete(taskId, variables);
		
		if(check==0){
			workordermanage.setStart("0");
			historyService.deleteHistoricProcessInstance(processInstanceId);
		}
	}
}
