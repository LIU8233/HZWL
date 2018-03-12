package com.hzwl.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.hzwl.controller.base.BaseAction;
import com.hzwl.entity.SystemManage;

@Controller
public class SystemManageAction extends BaseAction<SystemManage>{
	
	
	public String loadSystemMenu() throws IOException{
		
		List<SystemManage> list = systemManageService.findSystemMenu();
		
		String[] excludes=new String[]{};
		
		this.writeList2Json(list, excludes);
		
		return null;
	}

}
