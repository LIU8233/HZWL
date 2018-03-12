package com.hzwl.service;

import java.util.List;

import com.hzwl.entity.Noticebill;
import com.hzwl.entity.Staff;

public interface INoticebillService {

	/**
	 * 添加新单
	 * @param model
	 */
	public void save(Noticebill model);

	/**
	 * 查询没有取派员的通知单
	 * @return
	 */
	public List<Noticebill> findNoStaffNoticeBill();
	/**
	 * 根据通知单的id查找
	 * @param noticeBillId
	 * @return
	 */
	public Noticebill findNoticebillById(String noticeBillId);

	/**
	 * 给通知单分配取派员
	 * @param entity
	 * @param staff
	 */
	public void updateNoticebill(Noticebill entity,Staff staff);
}
