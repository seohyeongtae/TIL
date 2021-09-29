package com.example.user_ui;

public class receipts {
    String userid;
    String orderid;
    String orderdate;
    String itemname;
    int price;
    String category;
    String image;
    int quantity;
    String status;

    public receipts() {
    }

    public receipts(String orderid, String orderdate, String itemname, int price, String category, String image, int quantity, String status) {
        this.orderid = orderid;
        this.orderdate = orderdate;
        this.itemname = itemname;
        this.price = price;
        this.category = category;
        this.image = image;
        this.quantity = quantity;
        this.status = status;
    }

    public receipts(String userid, String orderid, String orderdate, String itemname, int price, String category, String image, int quantity, String status) {
        this.userid = userid;
        this.orderid = orderid;
        this.orderdate = orderdate;
        this.itemname = itemname;
        this.price = price;
        this.category = category;
        this.image = image;
        this.quantity = quantity;
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
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
}
