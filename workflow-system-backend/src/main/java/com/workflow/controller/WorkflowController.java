package com.workflow.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import com.workflow.model.Form;
import com.workflow.model.Role;
import com.workflow.model.User;
import com.workflow.model.Workflow;
import com.workflow.repository.FormRepository;
import com.workflow.repository.RoleRepository;
import com.workflow.repository.UserRepository;
import com.workflow.repository.WorkflowRepository;
import com.workflow.schema.FieldOption;
import com.workflow.schema.RootWorkflow;
import com.workflow.schema.Stage;
import com.workflow.security.WorkflowJwtUtil;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class WorkflowController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${secret.key}")
	private String m_secretKey;

	@Autowired
	private WorkflowRepository workflowRepository;

	@Autowired
	private FormRepository formRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/createWorkflow")
	public ResponseEntity<Map<String, Boolean>> createWorkflow(@RequestBody RootWorkflow rootWorkflow, @RequestHeader String authorization) {

		String token = authorization.split("Basic ")[1];
		// only AdminUser has rights to create workflow
		if (!WorkflowJwtUtil.isAdmin(token, m_secretKey)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cause description here");
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			Workflow workflow = new Workflow();
			workflow.setWorkflowName(rootWorkflow.getWorkflow().getName());
			String workflow_stage_metadata = mapper.writeValueAsString(rootWorkflow.getWorkflow().getStages());
			workflow.setWorkflowMetadata(workflow_stage_metadata);
			Set<Form> forms = new HashSet<Form>();
			for (com.workflow.schema.Form schemaForm : rootWorkflow.getWorkflow().getForms()) {
				Form form = new Form();
				form.setFormName(schemaForm.getName());
				form.setSignature(schemaForm.getSignature());
				String form_metadata = mapper.writeValueAsString(schemaForm.getFields());
				form.setFormMetadata(form_metadata);
				forms.add(form);
			}
			workflow.setForms(forms);
			workflowRepository.save(workflow);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, Boolean> response = new HashMap<>();
		response.put("created", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getWorkflow")
	public ResponseEntity<List<FieldOption>> getWorkflow(@RequestHeader String authorization) {
		String token = authorization.split("Basic ")[1];
		// only AdminUser has rights to get the list of workflow
		if (!WorkflowJwtUtil.isAdmin(token, m_secretKey)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cause description here");
		}

		List<Workflow> workflowList = workflowRepository.findAll();
		List<FieldOption> workflowNames = new ArrayList<FieldOption>();
		for (Workflow workflow : workflowList) {
			FieldOption fieldOption = new FieldOption();
			fieldOption.setOptionLabel(workflow.getWorkflowName());
			workflowNames.add(fieldOption);
		}
		return ResponseEntity.ok(workflowNames);
	}

	@GetMapping("/getWorkflowUsers/{workflowName}")
	public ResponseEntity<Map<String, List<String>>> getWorkflowUsers(@RequestHeader String authorization, @PathVariable String workflowName) {
		String token = authorization.split("Basic ")[1];
		// only AdminUser has rights to get the list of workflow
		if (!WorkflowJwtUtil.isAdmin(token, m_secretKey)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cause description here");
		}

		Workflow workflow = workflowRepository.findByWorkflowName(workflowName);
		ObjectMapper mapper = new ObjectMapper();

		Stage[] stageList;
		Map<String, List<String>> workflowUserMap = new HashMap<String, List<String>>();
		try {
			stageList = mapper.readValue(workflow.getWorkflowMetadata(), Stage[].class);
			for (Stage stage : stageList) {
				List<String> userList = new ArrayList<String>();
				if(stage.getUser() != null) {
					Role role = roleRepository.findByRoleName(stage.getUser());
					List<User> userDbList = userRepository.findByRole(role);
					for (User user : userDbList) {
						userList.add(user.getFirstName());
					}
					workflowUserMap.put(stage.getUser(), userList);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(workflowUserMap);
	}
	
	@GetMapping("/getWorkflowFileNames/{workflowName}")
	public ResponseEntity<Map<String, List<String>>> getWorkflowFileNames(@RequestHeader String authorization, @PathVariable String workflowName) {
		String token = authorization.split("Basic ")[1];
		// only AdminUser has rights to get the list of workflow
		if (!WorkflowJwtUtil.isAdmin(token, m_secretKey)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cause description here");
		}

		Workflow workflow = workflowRepository.findByWorkflowName(workflowName);
		Set<Form> formList = workflow.getForms();
		for (Form form: formList) {
			
		}
		
		
		ObjectMapper mapper = new ObjectMapper();

		Stage[] stageList;
		Map<String, List<String>> workflowUserMap = new HashMap<String, List<String>>();
		try {
			stageList = mapper.readValue(workflow.getWorkflowMetadata(), Stage[].class);
			for (Stage stage : stageList) {
				List<String> userList = new ArrayList<String>();
				if(stage.getUser() != null) {
					Role role = roleRepository.findByRoleName(stage.getUser());
					List<User> userDbList = userRepository.findByRole(role);
					for (User user : userDbList) {
						userList.add(user.getFirstName());
					}
					workflowUserMap.put(stage.getUser(), userList);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(workflowUserMap);
	}

}
