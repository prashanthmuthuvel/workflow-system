package com.workflow.schema;

public class Comment {

	private String userName;
	private String date;
	private String message;

	public Comment() {

	}

	public Comment(String userName, String date, String message) {
		super();
		this.userName = userName;
		this.date = date;
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
