package com.workflow.schema;

public class RootTicket {

	private Ticket ticket;

	public RootTicket() {

	}

	public RootTicket(Ticket ticket) {
		super();
		this.ticket = ticket;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;

	}

}
