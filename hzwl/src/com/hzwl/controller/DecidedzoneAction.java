package com.hzwl.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;

import cn.hzwl.crm.entity.Customer;

import com.hzwl.controller.base.BaseAction;
import com.hzwl.entity.Decidedzone;
import com.hzwl.entity.Staff;

@Controller
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	
	private String[] subareaid;
	private Integer[] customerIds;
	
	
	public Integer[] getCustomerIds() {
		return customerIds;
	}


	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}


	public String[] getSubareaid() {
		return subareaid;
	}


	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}


	/**
	 * 添加定区
	 * @return
	 */
	public String addDecidedzone(){
		decidedzonService.saveDecidedzone(model, subareaid);
		return "list";
	}
	
	/**
	 * 分页查询
	 * @return
	 * @throws IOException
	 */
	public String pageQuery() throws IOException{
		
		// 在查询之前，封装条件
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		
		String queryId=model.getId();
		Staff staff=model.getStaff();
		
		if (StringUtils.isNotBlank(queryId)) {
			// 按照地址关键字模糊查询
			detachedCriteria.add(Restrictions.like("id", "%"+queryId+"%"));
		}
		if(staff!=null){
			detachedCriteria.createAlias("staff", "s");
			
			String queryName=staff.getStation();
			
			if (StringUtils.isNotBlank(queryName)) {
				// 按照省进行模糊查询
				detachedCriteria.add(Restrictions.like("s.station", "%"
						+ queryName + "%"));
			}
		}
		
		decidedzonService.pageQuery(pageBean);
		
		String[] excludes={"decidedzones",
				"subareas", "currentPage", "detachedCriteria", "pageSize"};
		
		this.writePageBean2Json( pageBean , excludes);
		
		return null;
	}
	
	/**
	 * 查询没有关联的客户
	 * @return
	 * @throws IOException
	 */
	public String findNoAssociationCustomers() throws IOException{
		
		List<Customer> list = customerService.findNoAssociationCustomers();
		String[] excludes = new String[]{"station","address"};
		this.writeList2Json(list, excludes);
		
		return null;
	}
	
	/**
	 * 查询已经关联的客户
	 * @return
	 * @throws IOException
	 */
	public String findAssociationCustomers() throws IOException{
		
		List<Customer> list = customerService.findAssociationCustomers(model.getId());
		String[] excludes = new String[]{"station"};
		this.writeList2Json(list, excludes);
		
		return null;
	}
	/**
	 * 客户与定区关联
	 * @return
	 */
	public String assignCustomersToDecidedZone(){
		customerService.assignCustomersToDecidedZone(customerIds, model.getId());
		return "list";
	}
	
	
	/**
	 * 更新操作
	 * @return
	 */
	public String update(){
		decidedzonService.updataDecidedzone(model);
		return "list";
	}

	
	private String ids;
	
	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}


	/**
	 * 删除定区
	 * @return
	 */
	public String deleteDecidedzone(){
		decidedzonService.deleteDecidedzone(ids);
		return "list";
	}
}
