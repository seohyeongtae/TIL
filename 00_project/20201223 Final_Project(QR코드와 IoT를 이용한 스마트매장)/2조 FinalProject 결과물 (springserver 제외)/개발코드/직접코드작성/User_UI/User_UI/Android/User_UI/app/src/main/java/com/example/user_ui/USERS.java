package com.example.user_ui;

public class USERS {
    String userid;
    String pwd;
    int balance;
    String name;
    String gender;
    int age;
    String interest;

    public USERS() {
    }

    public USERS(String userid, String pwd, String name, String gender, int age) {
        this.userid = userid;
        this.pwd = pwd;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public USERS(String userid, String pwd, int balance, String name, String gender, int age) {
        this.userid = userid;
        this.pwd = pwd;
        this.balance = balance;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public USERS(String userid, String pwd, int balance, String name, String gender, int age, String interest) {
        this.userid = userid;
        this.pwd = pwd;
        this.balance = balance;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.interest = interest;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
