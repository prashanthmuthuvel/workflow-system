package com.workflow.schema;

public class Hybrid {

	private String formName;
	private String signature;
	private String decision;

	public Hybrid() {

	}

	public Hybrid(String formName, String signature, String decision) {
		super();
		this.formName = formName;
		this.signature = signature;
		this.decision = decision;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

}
