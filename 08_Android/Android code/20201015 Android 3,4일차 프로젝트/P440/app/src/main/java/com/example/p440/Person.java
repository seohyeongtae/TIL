package com.example.p440;

import java.util.Date;

public class Person {
    String name;
    String date;
    String phone;

    public Person() {
    }

    public Person(String name, String date, String phone) {
        this.name = name;
        this.date = date;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
