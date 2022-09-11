package com.workflow.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "filled_form")
public class FilledForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "form_id")
	private Form form;

	@Column(name = "filled_form_metadata", columnDefinition = "TEXT")
	private String filledFormMetadata;

	public FilledForm() {

	}

	public FilledForm(long id, Form form, String filledFormMetadata) {
		super();
		this.id = id;
		this.form = form;
		this.filledFormMetadata = filledFormMetadata;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public String getFilledFormMetadata() {
		return filledFormMetadata;
	}

	public void setFilledFormMetadata(String filledFormMetadata) {
		this.filledFormMetadata = filledFormMetadata;
	}

}
