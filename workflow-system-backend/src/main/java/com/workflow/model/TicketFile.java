package com.workflow.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ticket_file")
public class TicketFile {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String name;

	private String type;

	@Lob
	private byte[] data;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "form_id")
	private Form form;

	public TicketFile() {
	}

	public TicketFile(String name, String type, byte[] data) {
		this.name = name;
		this.type = type;
		this.data = data;
	}

	public TicketFile(String id, String name, String type, byte[] data, Form form) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.data = data;
		this.form = form;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

}
