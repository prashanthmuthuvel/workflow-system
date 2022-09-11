package com.workflow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflow.model.Login;
import com.workflow.model.User;
import com.workflow.repository.LoginRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LoginRepository loginRepository;

	// create student rest api
	@PostMapping("/students")
	public User createUser(@RequestBody User user) {
		Login login = new Login();
		login.setUsername(user.getEmailId());
		login.setPassword(user.getEmailId());
		login.setUser(user);
		loginRepository.save(login);
		logger.info("created user : " + user);
		return login.getUser();
	}

}
