package com.vo;

public class Interest {
	
	String gender;
	String data;
	public Interest() {
		super();
	}
	public Interest(String gender, String data) {
		super();
		this.gender = gender;
		this.data = data;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Interest [gender=" + gender + ", data=" + data + "]";
	}


	
	
}
