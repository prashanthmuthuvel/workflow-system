package com.workflow.schema;

import java.util.List;

public class Workflow {

	private String name;
	private List<Form> forms = null;
	private List<Stage> stages = null;

	public Workflow() {

	}

	public Workflow(String name, List<Form> forms, List<Stage> stages) {
		super();
		this.name = name;
		this.forms = forms;
		this.stages = stages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Form> getForms() {
		return forms;
	}

	public void setForms(List<Form> forms) {
		this.forms = forms;
	}

	public List<Stage> getStages() {
		return stages;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}

}
