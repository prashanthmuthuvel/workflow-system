package com.workflow.schema;

import java.util.List;

public class TicketForm {

	private long id;
	private String ticketTitle;
	private String currentStatus;
	private String createdBy;
	private String currentPerson;
	private String workflowName;
	private List<Form> formList;
	private List<FormInfo> formInfoList;
	private List<Decision> decisionList;
	private List<Comment> commentList;
	private List<TicketFlow> ticketFlowList;

	public TicketForm() {

	}

	public TicketForm(long id, String ticketTitle, String currentStatus, String createdBy, String currentPerson,
			String workflowName, List<Form> formList, List<FormInfo> formInfoList, List<Decision> decisionList,
			List<Comment> commentList, List<TicketFlow> ticketFlowList) {
		super();
		this.id = id;
		this.ticketTitle = ticketTitle;
		this.currentStatus = currentStatus;
		this.createdBy = createdBy;
		this.currentPerson = currentPerson;
		this.workflowName = workflowName;
		this.formList = formList;
		this.formInfoList = formInfoList;
		this.decisionList = decisionList;
		this.commentList = commentList;
		this.ticketFlowList = ticketFlowList;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public List<Decision> getDecisionList() {
		return decisionList;
	}

	public void setDecisionList(List<Decision> decisionList) {
		this.decisionList = decisionList;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTicketTitle() {
		return ticketTitle;
	}

	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCurrentPerson() {
		return currentPerson;
	}

	public void setCurrentPerson(String currentPerson) {
		this.currentPerson = currentPerson;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public List<Form> getFormList() {
		return formList;
	}

	public void setFormList(List<Form> formList) {
		this.formList = formList;
	}

	public List<FormInfo> getFormInfoList() {
		return formInfoList;
	}

	public void setFormInfoList(List<FormInfo> formInfoList) {
		this.formInfoList = formInfoList;
	}

	public List<TicketFlow> getTicketFlowList() {
		return ticketFlowList;
	}

	public void setTicketFlowList(List<TicketFlow> ticketFlowList) {
		this.ticketFlowList = ticketFlowList;
	}

}
