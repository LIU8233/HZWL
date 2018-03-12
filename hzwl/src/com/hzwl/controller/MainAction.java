package com.hzwl.controller;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;


@Controller
public class MainAction extends ActionSupport{
	
	
	public String main(){
		
		
		//System.out.println("----------------------------------");
		return "success";
	}

}
