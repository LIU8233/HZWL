package com.hzwl.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import cn.hzwl.crm.entity.Customer;

import com.hzwl.controller.base.BaseAction;
import com.hzwl.entity.Noticebill;
import com.hzwl.entity.Staff;
import com.hzwl.entity.User;

@Controller
public class NoticeBillAction extends BaseAction<Noticebill>{

	/**
	 * 添加通知单
	 * @return
	 */
	public String addNoticeBill(){
		
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("loginUser");
		model.setUser(user);
		noticeService.save(model);
		return SUCCESS;
	}
	/**
	 * 通过电话查询客户
	 * @return
	 * @throws IOException
	 */
	public String findCustomerByPhone() throws IOException{
		
		System.out.println("查找客户");
		Customer customer=customerService.findCustomerByPhone(model.getTelephone());
		String[] excludes = new String[]{};
		this.writeObject2Json(customer, excludes);
		
		return null;
	}
	
	/**
	 * 显示没有分配的通知单
	 * @return
	 * @throws IOException
	 */
	public String listNoStaffNoticeBill() throws IOException{
		
		List<Noticebill> list = noticeService.findNoStaffNoticeBill();
		String[] excludes = new String[]{"workbills","user","staff","customerId",
				"customerName","arrivecity","num","weight","volume","remark","ordertype"};
		this.writeList2Json(list, excludes);
		return null;
	}
	
	public String diaodu(){
		
		System.out.println(model.getId());
		System.out.println(model.getStaff().getId());
		
		String noticeBillId=model.getId();
		
		Staff staff = staffService.findById(model.getStaff().getId());
		
		Noticebill noticebill=noticeService.findNoticebillById(noticeBillId);
		
		noticebill.setStaff(staff);
		
		noticeService.updateNoticebill(noticebill,staff);
		return "list";
	}
	
	
}
