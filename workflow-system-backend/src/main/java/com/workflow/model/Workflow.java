package com.workflow.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "workflow")
public class Workflow {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "workflow_name")
	private String workflowName;
	
	@Column(name = "workflow_metadata", columnDefinition = "TEXT")
	private String workflowMetadata;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "workflow_id")
	private Set<Form> forms;
	
	public Workflow() {
		
	}

	public Workflow(long id, String workflowName, String workflowMetadata, Set<Form> forms) {
		super();
		this.id = id;
		this.workflowName = workflowName;
		this.workflowMetadata = workflowMetadata;
		this.forms = forms;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getWorkflowMetadata() {
		return workflowMetadata;
	}

	public void setWorkflowMetadata(String workflowMetadata) {
		this.workflowMetadata = workflowMetadata;
	}

	public Set<Form> getForms() {
		return forms;
	}

	public void setForms(Set<Form> forms) {
		this.forms = forms;
	}
	
	

}
