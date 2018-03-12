package com.hzwl.entity;

import java.io.Serializable;

import com.hzwl.utils.HibernateXMLAutoCreateUtils;

public class SystemManage implements Serializable{
	
	private String id;
	private String pId;
	private String name;
	private String page;
	private int zindex;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public int getZindex() {
		return zindex;
	}
	public void setZindex(int zindex) {
		this.zindex = zindex;
	}
	public SystemManage(String id, String pId, String name, String page,
			int zindex) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.page = page;
		this.zindex = zindex;
	}
	public SystemManage(String pId, String name, String page, int zindex) {
		super();
		this.pId = pId;
		this.name = name;
		this.page = page;
		this.zindex = zindex;
	}
	public SystemManage() {
		super();
	}
	
	public static void main(String[] args) {
		HibernateXMLAutoCreateUtils.createHibernatePOJOMapping(SystemManage.class, "id", "t_systemanage");
	}

}
