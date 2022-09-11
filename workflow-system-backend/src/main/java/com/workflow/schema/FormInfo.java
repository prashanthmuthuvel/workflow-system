package com.workflow.schema;

public class FormInfo {
	
	private int id;
	private String name;
	private String access;

	public FormInfo() {

	}

	public FormInfo(int id, String name, String access) {
		super();
		this.id = id;
		this.name = name;
		this.access = access;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

}
