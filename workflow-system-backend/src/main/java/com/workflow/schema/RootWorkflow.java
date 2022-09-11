package com.workflow.schema;

public class RootWorkflow {

	private Workflow workflow;
	
	public RootWorkflow() {
		
	}

	public RootWorkflow(Workflow workflow) {
		super();
		this.workflow = workflow;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;

	}

}
