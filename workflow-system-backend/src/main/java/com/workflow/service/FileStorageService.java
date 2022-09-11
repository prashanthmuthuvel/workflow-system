package com.workflow.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.workflow.model.Form;
import com.workflow.model.Ticket;
import com.workflow.model.TicketFile;
import com.workflow.repository.TicketFileRepository;
import com.workflow.repository.TicketRepository;

@Service
public class FileStorageService {

	@Autowired
	private TicketFileRepository ticketFileRepository;
	
	@Autowired
	private TicketRepository ticketRepository;

	public TicketFile store(Ticket ticket, MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		TicketFile FileDB = new TicketFile(fileName, file.getContentType(), file.getBytes());
		Set<TicketFile> ticketFiles = ticket.getTicketFiles();
		if(ticketFiles != null) {
			ticketFiles.add(FileDB);
		} else {
			ticketFiles = new HashSet<TicketFile>();
			ticketFiles.add(FileDB);
		}
		ticketRepository.save(ticket);
		return FileDB;
	}

	public TicketFile getFile(String id) {
		return ticketFileRepository.findById(id).get();
	}

	public Stream<TicketFile> getAllFiles() {
		return ticketFileRepository.findAll().stream();
	}

	public void deleteFile(String id) {
		ticketFileRepository.deleteById(id);
	}
	
	public void linkForm(String id, Form form) {
		TicketFile ticketFile = ticketFileRepository.findById(id).get();
		ticketFile.setForm(form);
		ticketFileRepository.save(ticketFile);
	}

}
