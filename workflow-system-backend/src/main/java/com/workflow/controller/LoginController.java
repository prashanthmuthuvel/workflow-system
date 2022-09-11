package com.workflow.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflow.constant.WorkflowConstant;
import com.workflow.model.Login;
import com.workflow.repository.LoginRepository;
import com.workflow.security.WorkflowJwtUtil;

import io.jsonwebtoken.Claims;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Value("${admin.username}")
	private String m_adminuser;
	
	@Value("${admin.password}")
	private String m_adminPassword;
	
	@Value("${session.timeout}")
	private Long m_sessionTimeout;
	
	@Value("${secret.key}")
	private String m_secretKey;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody Login login) {
		String token = null;
		Map<String, String> response = new HashMap<>();
		if (m_adminuser.equals(login.getUsername()) && m_adminPassword.equals(login.getPassword())) {
			logger.info(m_adminuser + " Login Successfull");
			token = WorkflowJwtUtil.createJWT(m_sessionTimeout, m_secretKey, true, login.getUsername());
			response.put(WorkflowConstant.TOKEN, token);
			response.put("user", "admin");
			response.put(WorkflowConstant.IS_ADMIN, "true");
			response.put(WorkflowConstant.IS_STUDENT, "false");
		} else {
			Login logindb = loginRepository.findByUsername(login.getUsername());
			if(logindb != null && logindb.getUsername().equals(login.getUsername()) && logindb.getPassword().equals(login.getPassword())) {
				logger.info(login.getUsername() + " Login Successfull");
				token = WorkflowJwtUtil.createJWT(m_sessionTimeout, m_secretKey, false, login.getUsername());
				response.put(WorkflowConstant.TOKEN, token);
				response.put("user", logindb.getUser().getFirstName());
				response.put(WorkflowConstant.IS_ADMIN, "false");
				response.put(WorkflowConstant.IS_STUDENT, "true");
			} else {
				logger.error("Tried to login with invalid username : " + login.getUsername() + " and password");
				response.put("error", "Username or Password is wrong");
			}
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/verifyToken")
	public ResponseEntity.BodyBuilder login(@PathVariable String token) {
		Claims claims = WorkflowJwtUtil.decodeJWT(token, m_secretKey);
		return ResponseEntity.ok();
	}

}
