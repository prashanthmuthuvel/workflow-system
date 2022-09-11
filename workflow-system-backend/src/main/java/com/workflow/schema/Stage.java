package com.workflow.schema;

import java.util.List;

public class Stage {

	private String name;
	private String user;
	private Hybrid hybrid;
	private List<FormInfo> formInfo = null;
	private List<Decision> decision = null;

	public Stage() {

	}

	public Stage(String name, String user, Hybrid hybrid, List<FormInfo> formInfo, List<Decision> decision) {
		super();
		this.name = name;
		this.user = user;
		this.hybrid = hybrid;
		this.formInfo = formInfo;
		this.decision = decision;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Hybrid getHybrid() {
		return hybrid;
	}

	public void setHybrid(Hybrid hybrid) {
		this.hybrid = hybrid;
	}

	public List<FormInfo> getFormInfo() {
		return formInfo;
	}

	public void setFormInfo(List<FormInfo> formInfo) {
		this.formInfo = formInfo;
	}

	public List<Decision> getDecision() {
		return decision;
	}

	public void setDecision(List<Decision> decision) {
		this.decision = decision;
	}

}
