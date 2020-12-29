package com.vo;

import org.springframework.web.multipart.MultipartFile;

public class Items {
	
	private String itemname;
	private int price;
	private int stock;
	private String category;
	private String image;
	MultipartFile mf; 
	
	public Items() {
		super();
	}

	public Items(String itemname, int price, int stock, String category, String image) {
		super();
		this.itemname = itemname;
		this.price = price;
		this.stock = stock;
		this.category = category;
		this.image = image;
	}
	
	

	public Items(String itemname, int stock) {
		super();
		this.itemname = itemname;
		this.stock = stock;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public MultipartFile getMf() {
		return mf;
	}

	
	public void setMf(MultipartFile mf) {
		this.mf = mf;
	}

	@Override
	public String toString() {
		return "Items [itemname=" + itemname + ", price=" + price + ", stock=" + stock + ", category=" + category
				+ ", image=" + image + ", mf=" + mf + "]";
	}
	
	
	
	
	
}
