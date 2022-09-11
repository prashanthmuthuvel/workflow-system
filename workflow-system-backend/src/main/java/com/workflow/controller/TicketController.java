package com.workflow.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.workflow.constant.WorkflowConstant;
import com.workflow.exception.ResourceNotFoundException;
import com.workflow.model.FilledForm;
import com.workflow.model.Form;
import com.workflow.model.Ticket;
import com.workflow.model.TicketBlockChain;
import com.workflow.model.TicketComment;
import com.workflow.model.User;
import com.workflow.model.Workflow;
import com.workflow.repository.FilledFormRepository;
import com.workflow.repository.LoginRepository;
import com.workflow.repository.TicketRepository;
import com.workflow.repository.UserRepository;
import com.workflow.repository.WorkflowRepository;
import com.workflow.schema.Comment;
import com.workflow.schema.Decision;
import com.workflow.schema.FormInfo;
import com.workflow.schema.Stage;
import com.workflow.schema.TicketFlow;
import com.workflow.schema.TicketForm;
import com.workflow.security.WorkflowJwtUtil;
import com.workflow.service.BlockService;
import com.workflow.service.RestClientUpload;
import com.workflow.service.TicketService;
import com.workflow.utils.DateUtil;

import io.jsonwebtoken.Claims;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class TicketController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${secret.key}")
	private String m_secretKey;

	@Autowired
	private WorkflowRepository workflowRepository;

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FilledFormRepository filledFormRepository;

	@Autowired
	private RestClientUpload restClientUpload;

	@Autowired
	private TicketService ticketService;

	@Autowired
	private BlockService blockService;

	@PostMapping("/createTicket")
	public ResponseEntity<Map<String, Long>> createTicket(@RequestBody com.workflow.schema.Ticket ticket,
			@RequestHeader String authorization) {

		String token = authorization.split("Basic ")[1];
		// only AdminUser has rights to create workflow
		if (!WorkflowJwtUtil.isAdmin(token, m_secretKey)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cause description here");
		}
		Claims claims = WorkflowJwtUtil.decodeJWT(authorization.split("Basic ")[1], m_secretKey);
		com.workflow.model.Login login = loginRepository.findByUsername((String) claims.get("username"));

		ObjectMapper mapper = new ObjectMapper();
		Ticket ticketDb = new Ticket();
		try {
			ticketDb.setTicketTitle(ticket.getTicketTitle());
			String userInDiffernetStage = mapper.writeValueAsString(ticket.getUserInDifferentStage());
			ticketDb.setUserInDifferentStage(userInDiffernetStage);
			ticketDb.setCreatedBy(login.getUser());
			Workflow workflow = workflowRepository.findByWorkflowName(ticket.getWorkflowName());
			ticketDb.setWorkflow(workflow);

			Stage[] stageList;
			stageList = mapper.readValue(workflow.getWorkflowMetadata(), Stage[].class);
			for (Stage stage : stageList) {
				if (stage.getName().equals(WorkflowConstant.STAGE1)) {
					ticketDb.setCurrentStage(WorkflowConstant.STAGE1);
					User user = null;
					if (stage.getUser().equals("student")) {
						user = userRepository.findByFirstName(ticket.getUserInDifferentStage().getStudent());
					} else if (stage.getUser().equals("professor")) {
						user = userRepository.findByFirstName(ticket.getUserInDifferentStage().getProfessor());
					} else if (stage.getUser().equals("chair")) {
						user = userRepository.findByFirstName(ticket.getUserInDifferentStage().getChair());
					}
					if (user == null) {
						// throw error messsage here saying current user not found
					}
					ticketDb.setCurrentPerson(user);
					ticketDb.setCurrentStatus(WorkflowConstant.IN_PROGRESS);
					break;
				}
			}

			List<TicketComment> ticketCommentList = new ArrayList<TicketComment>();
			TicketComment ticketComment = new TicketComment();
			ticketComment.setComment("Ticket Created");
			ticketComment.setCommentedBy(login.getUser());
			ticketComment.setCommentedOn(new Date());
			ticketCommentList.add(ticketComment);
			ticketDb.setTicketComments(ticketCommentList);

			Set<FilledForm> filledForms = new HashSet<FilledForm>();
			for (Form form : workflow.getForms()) {
				FilledForm filledForm = new FilledForm();
				filledForm.setFilledFormMetadata(form.getFormMetadata());
				filledForm.setForm(form);
				filledForms.add(filledForm);
			}
			ticketDb.setFilledForms(filledForms);

			blockService.addBlockIntoTicket(ticketDb,
					"Ticket created - currently in " + ticketDb.getCurrentPerson().getFirstName() + " "
							+ ticketDb.getCurrentPerson().getLastName() + " queue");

			ticketRepository.save(ticketDb);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info("Ticket Created with id " + ticketDb.getId() + " and currently with user " +ticketDb.getCurrentPerson().getFirstName());

		Map<String, Long> response = new HashMap<>();
		response.put("ticketId", ticketDb.getId());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getTicketList")
	public ResponseEntity<List<Ticket>> getTicketList(@RequestHeader String authorization) {
		String token = authorization.split("Basic ")[1];
		// only AdminUser has rights to get the list of workflow
		List<Ticket> ticketList = null;
		if (WorkflowJwtUtil.isAdmin(token, m_secretKey)) {
			ticketList = ticketRepository.findAll();
			return ResponseEntity.ok(ticketList);
		} else {
			Claims claims = WorkflowJwtUtil.decodeJWT(authorization.split("Basic ")[1], m_secretKey);
			com.workflow.model.Login login = loginRepository.findByUsername((String) claims.get("username"));

			ticketList = ticketRepository.findByCurrentPerson(login.getUser());

			return ResponseEntity.ok(ticketList);
		}
	}

	@GetMapping("/getTicketById/{id}")
	public ResponseEntity<TicketForm> getTicketById(@PathVariable Long id, @RequestHeader String authorization) {

		// TODO: is he authorized to get the form?

		Ticket ticket = ticketRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket not exist with id :" + id));
		Set<FilledForm> filledFormList = ticket.getFilledForms();
		TicketForm ticketForm = new TicketForm();
		ticketForm.setCreatedBy(ticket.getCreatedBy().getFirstName());
		if (ticket.getCurrentPerson() != null) {
			ticketForm.setCurrentPerson(ticket.getCurrentPerson().getFirstName());
		} else {
			ticketForm.setCurrentPerson("");
		}
		ticketForm.setCurrentStatus(ticket.getCurrentStatus());
		ticketForm.setTicketTitle(ticket.getTicketTitle());
		ticketForm.setWorkflowName(ticket.getWorkflow().getWorkflowName());
		List<com.workflow.schema.Form> ticketFilledFormList = new ArrayList<com.workflow.schema.Form>();
		ObjectMapper mapper = new ObjectMapper();
		for (FilledForm filledForm : filledFormList) {
			try {
				com.workflow.schema.Field[] fieldArray = mapper.readValue(filledForm.getFilledFormMetadata(),
						com.workflow.schema.Field[].class);
				com.workflow.schema.Form form = new com.workflow.schema.Form();
				form.setName(filledForm.getForm().getFormName());
				form.setFields(Arrays.asList(fieldArray));
				ticketFilledFormList.add(form);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ticketForm.setFormList(ticketFilledFormList);

		List<Comment> commentList = new ArrayList<Comment>();
		// get comments
		for (TicketComment ticketComment : ticket.getTicketComments()) {
			User user = ticketComment.getCommentedBy();
			Comment comment = new Comment();
			comment.setUserName(user.getFirstName() + " " + user.getLastName());
			comment.setMessage(ticketComment.getComment());
			comment.setDate(DateUtil.parseTimestamp(ticketComment.getCommentedOn()));
			commentList.add(comment);
		}
		ticketForm.setCommentList(commentList);

		Stage[] stageList;
		try {
			stageList = mapper.readValue(ticket.getWorkflow().getWorkflowMetadata(), Stage[].class);
			for (Stage stage : stageList) {
				if (ticket.getCurrentStage().equals(stage.getName())) {
					ticketForm.setFormInfoList(stage.getFormInfo());
					ticketForm.setDecisionList(stage.getDecision());
					for (FormInfo formInfo : ticketForm.getFormInfoList()) {
						for (FilledForm filledForm : filledFormList) {
							if (formInfo.getName().equals(filledForm.getForm().getFormName())) {
								formInfo.setId((int) filledForm.getId());
							}
						}

					}
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// validate the blockchain here
		// retrieve the inputs and set it into ticketForm TODO
		List<TicketFlow> ticketFlowList = new ArrayList<TicketFlow>();
		
		if (blockService.isChainValid(ticket.getTicketBlockChain())) {
			for(TicketBlockChain ticketBlockChain : ticket.getTicketBlockChain()) {
				TicketFlow ticketFlow = new TicketFlow();
				ticketFlow.setPath(ticketBlockChain.getData());
				ticketFlow.setDate(DateUtil.parseTimestamp(ticketBlockChain.getTimeStamp()));
				ticketFlowList.add(ticketFlow);
			}
			ticketForm.setTicketFlowList(ticketFlowList);
		} else {
			TicketFlow ticketFlow = new TicketFlow();
			ticketFlow.setPath("Data Breached");
			ticketFlow.setDate("");
			ticketFlowList.add(ticketFlow);
			ticketForm.setTicketFlowList(ticketFlowList);
			
			// once data is breached, no more decision can be made on the ticket
			List<Decision> decisionList = new ArrayList<Decision>();
			//Decision decision = new Decision();
			//decision.setName("");
			ticketForm.setDecisionList(decisionList);
		}
		
		String token = authorization.split("Basic ")[1];
		if (WorkflowJwtUtil.isAdmin(token, m_secretKey)) {
			List<Decision> decisionList = new ArrayList<Decision>();
			ticketForm.setDecisionList(decisionList);
		}

		return ResponseEntity.ok(ticketForm);
	}

	@GetMapping("/ticket/{id}/getForm/{formId}")
	public ResponseEntity<FilledForm> getForm(@PathVariable Long id, @PathVariable Long formId,
			@RequestHeader String authorization) {

		// TODO: is he authorized to get the form?
		FilledForm filledForm = filledFormRepository.findById(formId)
				.orElseThrow(() -> new ResourceNotFoundException("form not exist with id :" + id));

		return ResponseEntity.ok(filledForm);
	}

	@PostMapping("/ticket/{id}/storeForm/{formId}")
	public ResponseEntity<Map<String, Boolean>> storeForm(@RequestBody com.workflow.schema.Form form,
			@PathVariable Long id, @PathVariable Long formId, @RequestHeader String authorization) {

		FilledForm filledForm = filledFormRepository.findById(formId)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket not exist with id :" + formId));
		ObjectMapper mapper = new ObjectMapper();
		try {
			String form_metadata = mapper.writeValueAsString(form.getFields());
			filledForm.setFilledFormMetadata(form_metadata);
			filledFormRepository.save(filledForm);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, Boolean> response = new HashMap<>();
		response.put("stored form", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/ticketDecision/{id}")
	public ResponseEntity<Map<String, Boolean>> ticketDecision(@RequestBody String decision, @PathVariable Long id,
			@RequestHeader String authorization) {

		Claims claims = WorkflowJwtUtil.decodeJWT(authorization.split("Basic ")[1], m_secretKey);
		com.workflow.model.Login login = loginRepository.findByUsername((String) claims.get("username"));

		ticketService.ticketDecision(decision, id, "normal");

		Map<String, Boolean> response = new HashMap<>();
		response.put("created", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/extractData/{id}")
	public ResponseEntity<Map<String, Boolean>> extractData(@PathVariable Long id) {

		ticketService.extractData(id);

		Map<String, Boolean> response = new HashMap<>();
		response.put("created", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/postComment/{id}")
	public ResponseEntity<Map<String, Boolean>> postComment(@RequestBody String comment, @PathVariable Long id,
			@RequestHeader String authorization) {

		Claims claims = WorkflowJwtUtil.decodeJWT(authorization.split("Basic ")[1], m_secretKey);
		com.workflow.model.Login login = loginRepository.findByUsername((String) claims.get("username"));

		ticketService.postComment(id, comment, login.getUser());

		Map<String, Boolean> response = new HashMap<>();
		response.put("created", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
