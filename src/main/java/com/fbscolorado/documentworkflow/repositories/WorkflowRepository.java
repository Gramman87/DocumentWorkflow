package com.fbscolorado.documentworkflow.repositories;

import com.fbscolorado.documentworkflow.entities.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowRepository extends JpaRepository<Workflow, Integer> {
    List<Workflow> findWorkflowsByUsersId(Integer id);
}
