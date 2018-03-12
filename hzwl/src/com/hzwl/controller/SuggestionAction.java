package com.hzwl.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.hzwl.controller.base.BaseAction;
import com.hzwl.entity.Suggestion;
import com.opensymphony.xwork2.ActionContext;


@Controller
public class SuggestionAction extends BaseAction<Suggestion>{
	
	public String add(){
		
		suggestionService.addSuggestion(model);
		
		return null;
	}
	
	public String list() throws IOException{
		
		List<Suggestion> list = suggestionService.findAll();
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	
	public String delete(){
		suggestionService.delete(model.getId());
		return "toList";
	}
	
	public String edit(){
		suggestionService.updata(model.getId());
		return "toList";
	}

}
