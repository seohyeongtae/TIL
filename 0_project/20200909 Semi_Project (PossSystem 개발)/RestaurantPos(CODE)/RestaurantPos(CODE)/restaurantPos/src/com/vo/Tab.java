package com.vo;

public class Tab {
	private String id;
	private String pwd;
	private int name;
	private String admin_id;
	private int num;
	public Tab() {
		super();
	}
	public Tab(String id, String pwd) {
		super();
		this.id = id;
		this.pwd = pwd;
	}
	public Tab(String id, String pwd, int name, int num) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.num = num;
	}
	public Tab(String id, String pwd, int name) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
	}
	public Tab(String id, String pwd, int name, String admin_id, int num) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.admin_id = admin_id;
		this.num = num;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getName() {
		return name;
	}
	public void setName(int name) {
		this.name = name;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "Tab [id=" + id + ", pwd=" + pwd + ", name=" + name + ", admin_id=" + admin_id + ", num=" + num + "]";
	}
	
	
	
	
}
