package com.workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workflow.model.Role;
import com.workflow.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	List<User> findByRole(Role role);
	
	User findByFirstName(String firstName);

}
