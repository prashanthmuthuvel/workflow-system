package com.workflow.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.workflow.exception.ResourceNotFoundException;
import com.workflow.model.Form;
import com.workflow.model.Ticket;
import com.workflow.model.TicketFile;
import com.workflow.repository.TicketRepository;
import com.workflow.response.ResponseFile;
import com.workflow.response.ResponseMessage;
import com.workflow.schema.FieldOption;
import com.workflow.schema.FormFileInfo;
import com.workflow.service.FileStorageService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class TicketFileController {
	
	@Value("${secret.key}")
	private String m_secretKey;

	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	private TicketRepository ticketRepository;

	@PostMapping("/ticket/{ticketId}/file/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@PathVariable Long ticketId, @RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			Ticket ticket = ticketRepository.findById(ticketId)
					.orElseThrow(() -> new ResourceNotFoundException("Ticket not exist with id :" + ticketId));
			fileStorageService.store(ticket, file);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@GetMapping("/ticket/{ticketId}/files")
	public ResponseEntity<List<ResponseFile>> getListFiles(@PathVariable Long ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket not exist with id :" + ticketId));
		List<ResponseFile> files = ticket.getTicketFiles().stream().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/")
					.buildAndExpand(dbFile.getId()).toUriString();
			return new ResponseFile(dbFile.getId(), dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
		}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	@GetMapping("/file/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		TicketFile fileDB = fileStorageService.getFile(id);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
				.body(fileDB.getData());
	}

	@DeleteMapping("/file/delete/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteFile(@PathVariable String id) {
		fileStorageService.deleteFile(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/ticket/{ticketId}/getWorkflowForms")
	public ResponseEntity<List<FieldOption>> getWorkflowForms(@PathVariable Long ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket not exist with id :" + ticketId));
		
		
		List<FieldOption> workflowFormNames = new ArrayList<FieldOption>();
		for (Form form : ticket.getWorkflow().getForms()) {
			FieldOption fieldOption = new FieldOption();
			fieldOption.setOptionLabel(form.getFormName());
			workflowFormNames.add(fieldOption);
		}
		return ResponseEntity.ok(workflowFormNames);
	}
	
	@PostMapping("/ticket/{ticketId}/linkFileWithForm")
	public ResponseEntity<Map<String, Boolean>> linkFileWithForm(@RequestBody FormFileInfo formFileInfo, @PathVariable Long ticketId) {
		
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket not exist with id :" + ticketId));
		Form linkForm = null;
		for (Form form : ticket.getWorkflow().getForms()) {
			if(form.getFormName().equals(formFileInfo.getFormName())) {
				linkForm = form;
				break;
			}
		}
		fileStorageService.linkForm(formFileInfo.getFileId(), linkForm);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("linkedform", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
