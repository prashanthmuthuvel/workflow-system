package com.workflow.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "ticket_title")
	private String ticketTitle;

	@Column(name = "current_status")
	private String currentStatus;

	@Column(name = "current_stage")
	private String currentStage;

	@Column(name = "user_in_different_stage")
	private String userInDifferentStage;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "created_by")
	private User createdBy;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "current_person")
	private User currentPerson;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ticket_id")
	private List<TicketComment> ticketComments;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "workflow_id")
	private Workflow workflow;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ticket_id")
	private Set<TicketFile> ticketFiles;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ticket_id")
	private Set<FilledForm> filledForms;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ticket_id")
	private List<TicketBlockChain> ticketBlockChain;

	public Ticket() {

	}

	public Ticket(long id, String ticketTitle, String currentStatus, String currentStage, String userInDifferentStage,
			User createdBy, User currentPerson, List<TicketComment> ticketComments, Workflow workflow,
			Set<TicketFile> ticketFiles, Set<FilledForm> filledForms) {
		super();
		this.id = id;
		this.ticketTitle = ticketTitle;
		this.currentStatus = currentStatus;
		this.currentStage = currentStage;
		this.userInDifferentStage = userInDifferentStage;
		this.createdBy = createdBy;
		this.currentPerson = currentPerson;
		this.ticketComments = ticketComments;
		this.workflow = workflow;
		this.ticketFiles = ticketFiles;
		this.filledForms = filledForms;
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

	public String getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(String currentStage) {
		this.currentStage = currentStage;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public List<TicketComment> getTicketComments() {
		return ticketComments;
	}

	public void setTicketComments(List<TicketComment> ticketComments) {
		this.ticketComments = ticketComments;
	}

	public Set<TicketFile> getTicketFiles() {
		return ticketFiles;
	}

	public void setTicketFiles(Set<TicketFile> ticketFiles) {
		this.ticketFiles = ticketFiles;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public String getUserInDifferentStage() {
		return userInDifferentStage;
	}

	public void setUserInDifferentStage(String userInDifferentStage) {
		this.userInDifferentStage = userInDifferentStage;
	}

	public User getCurrentPerson() {
		return currentPerson;
	}

	public void setCurrentPerson(User currentPerson) {
		this.currentPerson = currentPerson;
	}

	public Set<FilledForm> getFilledForms() {
		return filledForms;
	}

	public void setFilledForms(Set<FilledForm> filledForms) {
		this.filledForms = filledForms;
	}

	public List<TicketBlockChain> getTicketBlockChain() {
		return ticketBlockChain;
	}

	public void setTicketBlockChain(List<TicketBlockChain> ticketBlockChain) {
		this.ticketBlockChain = ticketBlockChain;
	}

}