package com.hzwl.controller.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzwl.crm.CustomerService;
import com.hzwl.service.ICarService;
import com.hzwl.service.IDecidedzoneService;
import com.hzwl.service.IFunctionService;
import com.hzwl.service.INoticebillService;
import com.hzwl.service.IRegionService;
import com.hzwl.service.IRoleService;
import com.hzwl.service.IStaffService;
import com.hzwl.service.ISubareaService;
import com.hzwl.service.ISuggestionService;
import com.hzwl.service.ISystemManageService;
import com.hzwl.service.IWorkBillService;
import com.hzwl.service.IWorkordermanageService;
import com.hzwl.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 通用Action实现
 * 
 * 
 * 
 * @param <T>
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	// 注入Service
	@Autowired
	protected IRegionService regionService;
	@Autowired
	protected IStaffService staffService;
	@Autowired
	protected ISubareaService subareaService;
	@Autowired
	protected IDecidedzoneService decidedzonService;
	
	@Autowired
	protected CustomerService customerService;
	
	@Autowired
	protected INoticebillService noticeService;
	
	@Autowired
	protected IWorkordermanageService workService;
	
	@Autowired
	protected IFunctionService functionService;
	
	@Autowired
	protected IRoleService roleService;
	
	@Autowired
	protected IWorkBillService workbillService;
	
	@Autowired
	protected ICarService carService;
	
	@Autowired
	protected ISystemManageService systemManageService;
	
	@Autowired
	protected ISuggestionService suggestionService;
	
	
	protected PageBean pageBean = new PageBean();
	DetachedCriteria detachedCriteria = null;

	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}

	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}

	// 模型对象
	protected T model;

	public T getModel() {
		return model;
	}

	public void writePageBean2Json(PageBean pageBean, String[] excludes)
			throws IOException {
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}

	/**
	 * 在构造方法中动态获得实现类型，通过反射创建模型对象
	 */
	public BaseAction() {
		
		ParameterizedType genericSuperclass = null;
		
		if(this.getClass().getGenericSuperclass() instanceof ParameterizedType){
			genericSuperclass = (ParameterizedType) this
			.getClass().getGenericSuperclass();
		}else{//当前为Action创建了代理
			genericSuperclass = (ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass();
		}
		
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		// 获得实体类型
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		try {
			// 通过反射创建对象
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public  void writeList2Json(List list, String[] excludes) throws IOException {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(list, jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
	
	public void writeObject2Json(Object object, String[] excludes)
			throws IOException {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
}
