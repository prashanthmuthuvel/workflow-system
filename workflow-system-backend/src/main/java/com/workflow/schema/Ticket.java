package com.workflow.schema;

public class Ticket {

	private String ticketTitle;
	private UserInDifferentStage userInDifferentStage;
	private String workflowName;

	public Ticket() {

	}

	public Ticket(String ticketTitle, UserInDifferentStage userInDifferentStage, String workflowName) {
		super();
		this.ticketTitle = ticketTitle;
		this.userInDifferentStage = userInDifferentStage;
		this.workflowName = workflowName;
	}

	public String getTicketTitle() {
		return ticketTitle;
	}

	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}

	public UserInDifferentStage getUserInDifferentStage() {
		return userInDifferentStage;
	}

	public void setUserInDifferentStage(UserInDifferentStage userInDifferentStage) {
		this.userInDifferentStage = userInDifferentStage;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

}
