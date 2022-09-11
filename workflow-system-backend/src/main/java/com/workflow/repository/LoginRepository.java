package com.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workflow.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long>{
	
	Login findByUsername(String username);

}
