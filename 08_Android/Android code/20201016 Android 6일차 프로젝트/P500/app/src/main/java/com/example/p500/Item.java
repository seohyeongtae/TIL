package com.example.p500;


public class Item {
    String id;
    String name;
    int age;
    String img;
    String level;

    public Item() {
    }

    public Item(String id, String name, int age, String img, String level) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.img = img;
        this.level = level;
    }

    public Item(String id, String name, int age, String img) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.img = img;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
