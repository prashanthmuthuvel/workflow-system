package com.workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workflow.model.Ticket;
import com.workflow.model.TicketFile;

@Repository
public interface TicketFileRepository extends JpaRepository<TicketFile, String>{

}