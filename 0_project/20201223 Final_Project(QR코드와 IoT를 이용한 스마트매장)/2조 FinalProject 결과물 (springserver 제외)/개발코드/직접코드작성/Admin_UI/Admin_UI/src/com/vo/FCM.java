package com.vo;

import org.springframework.web.multipart.MultipartFile;

public class FCM {
	
	private String title;
	private String title2;
	private String data;
	private String gender;
	private String category;
	
	public FCM() {
		super();
	}

	public FCM(String title, String title2, String data, String gender, String category) {
		super();
		this.title = title;
		this.title2 = title2;
		this.data = data;
		this.gender = gender;
		this.category = category;
	}

	public FCM(String title, String title2, String data, String gender) {
		super();
		this.title = title;
		this.title2 = title2;
		this.data = data;
		this.gender = gender;
	}

	public FCM(String title, String title2, String data) {
		super();
		this.title = title;
		this.title2 = title2;
		this.data = data;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "FCM [title=" + title + ", title2=" + title2 + ", data=" + data + ", gender=" + gender + ", category="
				+ category + "]";
	}
	
	
	
}
