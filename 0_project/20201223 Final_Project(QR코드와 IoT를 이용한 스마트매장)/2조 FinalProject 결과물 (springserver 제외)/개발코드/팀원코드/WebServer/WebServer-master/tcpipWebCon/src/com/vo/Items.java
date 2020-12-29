package com.vo;

public class Items {
	private String itemname;
	private int price;
	private int stock;
	private String category;
	private String image;
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
	@Override
	public String toString() {
		return "Items [itemname=" + itemname + ", price=" + price + ", stock=" + stock + ", category=" + category
				+ ", image=" + image + "]";
	}
	
	
	
}
