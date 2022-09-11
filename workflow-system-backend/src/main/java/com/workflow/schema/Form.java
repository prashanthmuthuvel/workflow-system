package com.workflow.schema;

import java.util.List;

public class Form {

	private String name;
	private String signature;
	private List<Field> fields = null;

	public Form() {

	}

	public Form(String name, String signature, List<Field> fields) {
		super();
		this.name = name;
		this.signature = signature;
		this.fields = fields;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

}
