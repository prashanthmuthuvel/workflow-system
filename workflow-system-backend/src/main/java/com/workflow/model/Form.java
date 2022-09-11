package com.workflow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "form")
public class Form {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "form_name")
	private String formName;
	
	@Column(name = "signature")
	private String signature;

	@Column(name = "form_metadata", columnDefinition = "TEXT")
	private String formMetadata;

	public Form() {

	}

	public Form(long id, String formName, String signature, String formMetadata) {
		super();
		this.id = id;
		this.formName = formName;
		this.signature = signature;
		this.formMetadata = formMetadata;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getFormMetadata() {
		return formMetadata;
	}

	public void setFormMetadata(String formMetadata) {
		this.formMetadata = formMetadata;
	}

}
