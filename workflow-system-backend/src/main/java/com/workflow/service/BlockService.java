package com.workflow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.workflow.blockchain.Block;
import com.workflow.model.Ticket;
import com.workflow.model.TicketBlockChain;

@Service
public class BlockService {
	
	public static int difficulty = 5;
	
	public void addBlockIntoTicket(Ticket ticket, String data) {
		List<TicketBlockChain> blockChainList = ticket.getTicketBlockChain();
		if(blockChainList == null) {
			blockChainList = new ArrayList<TicketBlockChain>();
			Block block = new Block(data, "0");
			block.mineBlock(difficulty);
			
			TicketBlockChain blockChain = createTicketBlockChainObjectFromBlock(block);
			blockChainList.add(blockChain);
			
			ticket.setTicketBlockChain(blockChainList);
		} else {
			TicketBlockChain lastblock = blockChainList.get(blockChainList.size() - 1);
			Block block = new Block(data, lastblock.hash);
			block.mineBlock(difficulty);
			TicketBlockChain blockChain = createTicketBlockChainObjectFromBlock(block);
			blockChainList.add(blockChain);
		}
		ticket.setTicketBlockChain(blockChainList);
	}
	
	public TicketBlockChain createTicketBlockChainObjectFromBlock(Block block) {
		TicketBlockChain ticketBlockChain = new TicketBlockChain();
		ticketBlockChain.setData(block.getData());
		ticketBlockChain.setHash(block.getHash());
		ticketBlockChain.setNonce(block.getNonce());
		ticketBlockChain.setPreviousHash(block.getPreviousHash());
		ticketBlockChain.setTimeStamp(new Date());
		return ticketBlockChain;
	}
	
	public Boolean isChainValid(List<TicketBlockChain> blockChainList) {
		TicketBlockChain currentBlock; 
		TicketBlockChain previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockChainList.size(); i++) {
			currentBlock = blockChainList.get(i);
			previousBlock = blockChainList.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
			
		}
		return true;
	}

}
