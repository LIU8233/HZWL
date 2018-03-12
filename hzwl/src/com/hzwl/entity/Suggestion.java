package com.hzwl.entity;

import java.io.Serializable;

import com.hzwl.utils.HibernateXMLAutoCreateUtils;

public class Suggestion implements Serializable{
	
	private int id;
	private String idea;
	private String handle="0";//标志位0未处理，1已处理
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdea() {
		return idea;
	}
	public void setIdea(String idea) {
		this.idea = idea;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public Suggestion(int id, String idea, String handle) {
		super();
		this.id = id;
		this.idea = idea;
		this.handle = handle;
	}
	public Suggestion(String idea, String handle) {
		super();
		this.idea = idea;
		this.handle = handle;
	}
	public Suggestion() {
		super();
	}
	
	public static void main(String[] args) {
		HibernateXMLAutoCreateUtils.createHibernatePOJOMapping(Suggestion.class, "id", "t_suggestion");
	}
	

}
