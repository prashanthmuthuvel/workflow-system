package com.workflow.blockchain;

import java.util.Date;

import com.workflow.utils.StringUtil;

public class Block {

	public String hash;
	public String previousHash;
	private String data; // our data will be a simple message.
	private int nonce;
	public static int difficulty = 5;

	// Block Constructor.
	public Block(String data, String previousHash) {
		this.data = data;
		this.previousHash = previousHash;

		this.hash = calculateHash(); // Making sure we do this after we set the other values.
	}

	// Calculate new hash based on blocks contents
	public String calculateHash() {
		String calculatedhash = StringUtil
				.applySha256(previousHash + Integer.toString(nonce) + data);
		return calculatedhash;
	}

	// Increases nonce value until hash target is reached.
	public void mineBlock(int difficulty) {
		String target = StringUtil.getDificultyString(difficulty); // Create a string with difficulty * "0"
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
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

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public static int getDifficulty() {
		return difficulty;
	}

	public static void setDifficulty(int difficulty) {
		Block.difficulty = difficulty;
	}

}
