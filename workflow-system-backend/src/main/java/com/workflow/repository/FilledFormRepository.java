package com.workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workflow.model.FilledForm;
import com.workflow.model.Ticket;
import com.workflow.model.User;

@Repository
public interface FilledFormRepository extends JpaRepository<FilledForm, Long>{

}
