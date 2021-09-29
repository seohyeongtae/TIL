package com.vo;

import org.springframework.web.multipart.MultipartFile;

public class Menu {

	private String id;
	private String name;
	private int price;
	private int tsales;
	private int category;
	private String img1;
	private String img2;
	private String img3;
	MultipartFile mf; 
	
	
	public Menu() {
		super();
	}


	public Menu(String id, String name, int price, int category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
	}


	public Menu(String id, String name, int price, int tsales, int category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.tsales = tsales;
		this.category = category;
	}


	public Menu(String id, String name, int price, int tsales, int category, String img1) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.tsales = tsales;
		this.category = category;
		this.img1 = img1;
	}


	public Menu(String id, String name, int price, int tsales, int category, String img1, String img2) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.tsales = tsales;
		this.category = category;
		this.img1 = img1;
		this.img2 = img2;
	}


	public Menu(String id, String name, int price, int tsales, int category, String img1, String img2, String img3) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.tsales = tsales;
		this.category = category;
		this.img1 = img1;
		this.img2 = img2;
		this.img3 = img3;
	}


	public Menu(String id, String name, int price, int category, String img1) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.img1 = img1;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getTsales() {
		return tsales;
	}


	public void setTsales(int tsales) {
		this.tsales = tsales;
	}


	public int getCategory() {
		return category;
	}


	public void setCategory(int category) {
		this.category = category;
	}


	public String getImg1() {
		return img1;
	}


	public void setImg1(String img1) {
		this.img1 = img1;
	}


	public String getImg2() {
		return img2;
	}


	public void setImg2(String img2) {
		this.img2 = img2;
	}


	public String getImg3() {
		return img3;
	}


	public void setImg3(String img3) {
		this.img3 = img3;
	}

	
	public MultipartFile getMf() {
		return mf;
	}

	
	public void setMf(MultipartFile mf) {
		this.mf = mf;
	}

	
	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", price=" + price + ", tsales=" + tsales + ", category="
				+ category + ", img1=" + img1 + ", img2=" + img2 + ", img3=" + img3 + ", mf=" + mf + "]";
	}
	

	
}
