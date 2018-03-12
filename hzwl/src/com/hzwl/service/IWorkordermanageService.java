package com.hzwl.service;


import java.util.List;

import com.hzwl.entity.Workordermanage;

public interface IWorkordermanageService {

	/**
	 * 添加新的工作单
	 * @param model
	 */
	public void save(Workordermanage model);

	/**
	 * 查询没有启动的工单
	 * @return
	 */
	public List<Workordermanage> findNoStart();

	/**
	 * 启动流程
	 * @param id
	 */
	public void start(String id);

	public Workordermanage findById(String workordermanageId);

	public void checkWorkordermanage(String taskId, Integer check,
			String workordermanageId);

}
