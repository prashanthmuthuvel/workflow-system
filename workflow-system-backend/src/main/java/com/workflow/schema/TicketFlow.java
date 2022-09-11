package com.workflow.schema;

public class TicketFlow {

	private String path;
	private String date;

	public TicketFlow() {

	}

	public TicketFlow(String path, String date) {
		super();
		this.path = path;
		this.date = date;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
