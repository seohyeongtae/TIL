package com.example.user_ui;

import java.io.Serializable;

public class item implements Serializable {
    String name;
    String price;
    String stock;
    String category;
    String img;
    int order;

    public item() {
    }

    public item(String name, String price, String stock, String category, String img) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.img = img;
    }

    public item(String name, String price, String stock, String category, String img, int order) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.img = img;
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
