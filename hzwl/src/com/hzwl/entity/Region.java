package com.hzwl.entity;

import java.util.HashSet;
import java.util.Set;
/**
 * 区域实体
 * 
 *
 */

public class Region implements java.io.Serializable {

	// Fields

	private String id;
	private String province;//省
	private String city;//市
	private String district;//县或区
	private String postcode;//邮编
	private Set subareas = new HashSet(0);
	
	public String getName(){
		return province+city + district;
	}
	

	// Constructors

	/** default constructor */
	public Region() {
	}

	/** minimal constructor */
	public Region(String id) {
		this.id = id;
	}

	/** full constructor */
	public Region(String id, String province, String city, String district,
			String postcode, Set subareas) {
		this.id = id;
		this.province = province;
		this.city = city;
		this.district = district;
		this.postcode = postcode;
		this.subareas = subareas;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}


	public Set getSubareas() {
		return this.subareas;
	}

	public void setSubareas(Set subareas) {
		this.subareas = subareas;
	}

	@Override
	public String toString() {
		return "Region [id=" + id + ", province=" + province + ", city=" + city
				+ ", district=" + district + ", postcode=" + postcode
				+ ", subareas=" + subareas + "]";
	}

}