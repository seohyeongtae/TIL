package com.vo;

import java.sql.Date;

public class Orders {
	
	private String orderid;
	private String userid;
	private String orderdate;
	private int totalprice;
	
	public Orders() {
		super();
	}

	public Orders(String orderid, String userid, String orderdate, int totalprice) {
		super();
		this.orderid = orderid;
		this.userid = userid;
		this.orderdate = orderdate;
		this.totalprice = totalprice;
	}
	
	

	public Orders(String orderdate, int totalprice) {
		super();
		this.orderdate = orderdate;
		this.totalprice = totalprice;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public int getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}

	@Override
	public String toString() {
		return "Orders [orderid=" + orderid + ", userid=" + userid + ", orderdate=" + orderdate + ", totalprice="
				+ totalprice + "]";
	}
	
	
	
}
