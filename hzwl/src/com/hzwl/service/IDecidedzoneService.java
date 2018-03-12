package com.hzwl.service;

import java.util.List;

import com.hzwl.entity.Decidedzone;
import com.hzwl.utils.PageBean;

public interface IDecidedzoneService {

	public void saveDecidedzone(Decidedzone model, String[] subareaid);

	public void pageQuery(PageBean pageBean);
	
	public void updataDecidedzone(Decidedzone model);

	public void deleteDecidedzone(String ids);
	
	public List<Decidedzone> findDecidedzoneById(String decidedzoneId);
}
