package com.workflow.schema;

public class Decision {

	private String name;
	private String moveTo;
	private String status;

	public Decision() {

	}

	public Decision(String name, String moveTo, String status) {
		super();
		this.name = name;
		this.moveTo = moveTo;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMoveTo() {
		return moveTo;
	}

	public void setMoveTo(String moveTo) {
		this.moveTo = moveTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;

	}
}
