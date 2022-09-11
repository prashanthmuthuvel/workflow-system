package com.workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workflow.model.Form;
import com.workflow.model.Workflow;

@Repository
public interface FormRepository extends JpaRepository<Form, Long>{

}
