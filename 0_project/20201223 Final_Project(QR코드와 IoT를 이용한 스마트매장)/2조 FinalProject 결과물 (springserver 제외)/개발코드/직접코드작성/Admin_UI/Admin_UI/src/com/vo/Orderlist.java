package com.vo;

public class Orderlist {
	
	private String orderid;
	private String itemname;
	private int quantity;
	private String status;
	public Orderlist() {
		super();
	}
	public Orderlist(String orderid, String itemname, int quantity, String status) {
		super();
		this.orderid = orderid;
		this.itemname = itemname;
		this.quantity = quantity;
		this.status = status;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Orderlist [orderid=" + orderid + ", itemname=" + itemname + ", quantity=" + quantity + ", status="
				+ status + "]";
	}
	
	
	

}
