package com.hzwl.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzwl.crm.CustomerService;
import com.hzwl.dao.IDecidedzoneDao;
import com.hzwl.dao.INoticebillDao;
import com.hzwl.dao.IWorkbillDao;
import com.hzwl.entity.Decidedzone;
import com.hzwl.entity.Noticebill;
import com.hzwl.entity.Staff;
import com.hzwl.entity.Workbill;
import com.hzwl.service.INoticebillService;

@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService{
	
	//注入dao
	@Autowired
	private INoticebillDao noticebillDao;//通知单
	@Autowired
	private CustomerService proxy;//客户服务
	@Autowired
	private IDecidedzoneDao decidedzoneDao;//定区
	@Autowired
	private IWorkbillDao workbillDao;//工单
	/**
	 * 保存业务通知单，尝试自动分单
	 * @return
	 */
	public void save(Noticebill model) {
		
		noticebillDao.save(model);
		//获取取件地址
		String pickaddress = model.getPickaddress();
		
		//根据取件地址查询定区id---从crm服务查询 
		String dId = proxy.findDecidedzoneIdByPickaddress(pickaddress);
		
		if(dId != null){
			//查询到定区id，可以自动分单
			Decidedzone decidedzone = decidedzoneDao.findById(dId);
			
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);//业务通知单关联匹配到的取派员
			model.setOrdertype("自动");//分单类型
			//需要为取派员创建一个工单
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//工单创建的时间
			workbill.setNoticebill(model);//工单关联业务通知单
			workbill.setPickstate("未取件");//取件状态
			workbill.setRemark(model.getRemark());//备注
			workbill.setStaff(staff);//工单关联取派员
			workbill.setType("新单");
			
			workbillDao.save(workbill);//保存工单
			
		}else{
			//没有查询到定区id，转为人工分单
			model.setOrdertype("人工");
		}
	}
	
	@Override
	public List<Noticebill> findNoStaffNoticeBill() {
		// TODO Auto-generated method stub
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Noticebill.class);
		detachedCriteria.add(Restrictions.isNull("staff"));
		return noticebillDao.findByAnyQuery(detachedCriteria);
	}

	@Override
	public Noticebill findNoticebillById(String noticeBillId) {
		// TODO Auto-generated method stub
		return noticebillDao.findById(noticeBillId);
	}

	@Override
	public void updateNoticebill(Noticebill entity,Staff staff) {
		// TODO Auto-generated method stub
		
		noticebillDao.update(entity);
		
		Workbill workbill = new Workbill();
		
		workbill.setAttachbilltimes(0);//追单次数
		
		workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//工单创建的时间
		
		workbill.setNoticebill(entity);//工单关联业务通知单
		
		workbill.setPickstate("未取件");//取件状态
		
		workbill.setRemark(entity.getRemark());//备注
		
		workbill.setStaff(staff);//工单关联取派员
		
		workbill.setType("新单");
		
		workbillDao.save(workbill);//保存工单
		
	}
}
