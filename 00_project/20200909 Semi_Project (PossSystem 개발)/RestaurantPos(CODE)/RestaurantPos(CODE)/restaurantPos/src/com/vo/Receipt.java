package com.vo;

public class Receipt {

	private String id;
	private String tab_id;
	private String regdate;
	private int payment;
	private int total;

	
	public Receipt() {
		super();
	}
	public Receipt(String id, String tab_id, int payment, int total) {
		super();
		this.id = id;
		this.tab_id = tab_id;
		this.payment = payment;
		this.total = total;
	}
	public Receipt(String id, String tab_id, String regdate, int payment, int total) {
		super();
		this.id = id;
		this.tab_id = tab_id;
		this.regdate = regdate;
		this.payment = payment;
		this.total = total;
	}
	
	public Receipt(String tab_id, int payment, int total) {
		super();
		this.tab_id = tab_id;
		this.payment = payment;
		this.total = total;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getTab_id() {
		return tab_id;
	}
	public void setTab_id(String tab_id) {
		this.tab_id = tab_id;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getPayment() {
		return payment;
	}
	public void setPayment(int payment) {
		this.payment = payment;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "Receipt [id=" + id + ", tab_id=" + tab_id + ", regdate=" + regdate + ", payment=" + payment + ", total="
				+ total + "]";
	}
	
	
}
