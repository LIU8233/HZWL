package com.hzwl.entity;

import com.hzwl.utils.HibernateXMLAutoCreateUtils;

public class Car implements java.io.Serializable{
	
	private int id;
	private String cartype;
	private String carnumber;
	private String carname;
	private String driver;
	private int nuclearload;
	private String mycheck="0";
	
	public Car() {
		super();
	}
	
	public Car(String cartype, String carnumber, String carname, String driver,
			Integer nuclearload, String mycheck) {
		super();
		this.cartype = cartype;
		this.carnumber = carnumber;
		this.carname = carname;
		this.driver = driver;
		this.nuclearload = nuclearload;
		this.mycheck = mycheck;
	}

	public Car(int id, String cartype, String carnumber, String carname,
			String driver, Integer nuclearload, String mycheck) {
		super();
		this.id = id;
		this.cartype = cartype;
		this.carnumber = carnumber;
		this.carname = carname;
		this.driver = driver;
		this.nuclearload = nuclearload;
		this.mycheck = mycheck;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCartype() {
		return cartype;
	}
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	public String getCarnumber() {
		return carnumber;
	}
	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}
	public String getCarname() {
		return carname;
	}
	public void setCarname(String carname) {
		this.carname = carname;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public Integer getNuclearload() {
		return nuclearload;
	}
	public void setNuclearload(Integer nuclearload) {
		this.nuclearload = nuclearload;
	}
	

	public String getMycheck() {
		return mycheck;
	}

	public void setMycheck(String mycheck) {
		this.mycheck = mycheck;
	}

	public static void main(String[] args) {
		HibernateXMLAutoCreateUtils.createHibernatePOJOMapping(Car.class, "id", "t_car");
	}
	
}
