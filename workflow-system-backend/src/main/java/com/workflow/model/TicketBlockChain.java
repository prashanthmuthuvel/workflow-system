package com.workflow.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.workflow.utils.StringUtil;

@Entity
@Table(name = "ticket_blockchain")
public class TicketBlockChain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "hash")
	public String hash;

	@Column(name = "previous_hash")
	public String previousHash;

	@Column(name = "data")
	private String data; // our data will be a simple message.

	@Column(name = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp; // as number of milliseconds since 1/1/1970.

	@Column(name = "nounce")
	private int nonce;

	public TicketBlockChain() {

	}

	public TicketBlockChain(long id, String hash, String previousHash, String data, Date timeStamp, int nonce) {
		super();
		this.id = id;
		this.hash = hash;
		this.previousHash = previousHash;
		this.data = data;
		this.timeStamp = timeStamp;
		this.nonce = nonce;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	// Calculate new hash based on blocks contents
	public String calculateHash() {
		String calculatedhash = StringUtil
				.applySha256(previousHash + Integer.toString(nonce) + data);
		return calculatedhash;
	}

}
