package com.hzwl.service;

import java.util.List;

import com.hzwl.entity.Subarea;
import com.hzwl.utils.PageBean;

public interface ISubareaService {

	public void save(Subarea model);
	
	public void editSubarea(Subarea model);
	
	public void deleteSubarea(String ids);

	public void pageQuery(PageBean pageBean);

	public List<Subarea> findAll();
	
	public List<Subarea> findNotAssociation();
	
	public List<Subarea> findAssoctionDecidedzone(String decidedzoneId);

}
