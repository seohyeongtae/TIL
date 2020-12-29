package com.vo;

public class Users {
	
	private String userid;
	private String pwd;
	private int balance;
	private String name;
	private String gender;
	private int age;
	
	
	public Users() {
		super();
	}


	public Users(String userid, String pwd, int balance, String name, String gender, int age) {
		super();
		this.userid = userid;
		this.pwd = pwd;
		this.balance = balance;
		this.name = name;
		this.gender = gender;
		this.age = age;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public int getBalance() {
		return balance;
	}


	public void setBalance(int balance) {
		this.balance = balance;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	@Override
	public String toString() {
		return "Users [userid=" + userid + ", pwd=" + pwd + ", balance=" + balance + ", name=" + name + ", gender="
				+ gender + ", age=" + age + "]";
	}
	
	
	

}
