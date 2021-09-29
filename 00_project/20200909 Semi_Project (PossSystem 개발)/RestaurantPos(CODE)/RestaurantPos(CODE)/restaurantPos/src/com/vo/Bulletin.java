package com.vo;

import java.util.Date;

public class Bulletin {

	private int id;
	private String tab_id;
	private String title;
	private String content;
	private String author;
	private Date regdate;
	private int comment_id;
	
	public Bulletin() {
		super();
	}

	public Bulletin(int id, String tab_id, String title, String content, String author) {
		super();
		this.id = id;
		this.tab_id = tab_id;
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public Bulletin(int id, String tab_id, String title, String content, String author, Date regdate) {
		super();
		this.id = id;
		this.tab_id = tab_id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.regdate = regdate;
	}

	public Bulletin(int id, String tab_id, String title, String content, String author, int comment_id) {
		super();
		this.id = id;
		this.tab_id = tab_id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.comment_id = comment_id;
	}

	public Bulletin(int id, String tab_id, String title, String content, String author, Date regdate, int comment_id) {
		super();
		this.id = id;
		this.tab_id = tab_id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.regdate = regdate;
		this.comment_id = comment_id;
	}
	
	

	public Bulletin(String tab_id, String title, String content, String author) {
		super();
		this.tab_id = tab_id;
		this.title = title;
		this.content = content;
		this.author = author;
	}
	
	

	public Bulletin(String tab_id, String title, String content, String author, int comment_id) {
		super();
		this.tab_id = tab_id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.comment_id = comment_id;
	}
	//Ãß°¡
	public Bulletin(String title, String content, String author) {
		super();
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTab_id() {
		return tab_id;
	}

	public void setTab_id(String tab_id) {
		this.tab_id = tab_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	@Override
	public String toString() {
		return "Bulletin [id=" + id + ", tab_id=" + tab_id + ", title=" + title + ", content=" + content + ", author="
				+ author + ", regdate=" + regdate + ", comment_id=" + comment_id + "]";
	}
	
	
	
}
