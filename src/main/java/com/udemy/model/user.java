package com.udemy.model;

import javax.persistence.Column;

public class user extends ParentEntity {

	private static final long serialVersionUID = 970516850209393157L;
	
	@Column(name="firstname",nullable=false,length=255)
	private String firstname;
	@Column(name="secondname",nullable=false,length=255)	
	private String secondname;
	@Column(name="firstsurname",nullable=false,length=255)	
	private String firstsurname;
	@Column(name="secondsurname",nullable=false,length=255)	
	private String secondsurname;
	@Column(name="phone",nullable=false,length=255)	
	private String phone;
	@Column(name="address",nullable=false,length=255)
	private String address;
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSecondname() {
		return secondname;
	}
	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}
	public String getFirstsurname() {
		return firstsurname;
	}
	public void setFirstsurname(String firstsurname) {
		this.firstsurname = firstsurname;
	}
	public String getSecondsurname() {
		return secondsurname;
	}
	public void setSecondsurname(String secondsurname) {
		this.secondsurname = secondsurname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
