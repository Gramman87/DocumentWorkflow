package com.fbscolorado.documentworkflow.services;

import com.fbscolorado.documentworkflow.entities.Document;
import com.fbscolorado.documentworkflow.entities.User;
import com.fbscolorado.documentworkflow.entities.Workflow;
import com.fbscolorado.documentworkflow.entities.WorkflowType;
import com.fbscolorado.documentworkflow.repositories.DocumentRepository;
import com.fbscolorado.documentworkflow.repositories.UserRepository;
import com.fbscolorado.documentworkflow.repositories.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkflowServiceImpl implements WorkflowService{
    @Autowired
    private WorkflowRepository workflowRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private DocumentRepository documentRepo;

    @Override
    public Workflow findById(int id) {
        Optional<Workflow> optWorkflow = workflowRepo.findById(id);
        return optWorkflow.orElse(null);
    }

    @Override
    public Workflow createWorkflow(Workflow workflow) {
        return workflowRepo.saveAndFlush(workflow);
    }

    @Override
    public Workflow updateWorkflow(int id, Workflow workflow) {
        Optional<Workflow> optWorkflow = workflowRepo.findById(id);
        Workflow managedWorkflow;
        if (optWorkflow.isPresent()) {
            managedWorkflow = optWorkflow.get();
            managedWorkflow.setCurrentUser(workflow.getCurrentUser());
            managedWorkflow.setNextUser(workflow.getNextUser());
            if (workflow.getWorkflowType() != null) {
                managedWorkflow.setWorkflowType(workflow.getWorkflowType());
            }
            workflowRepo.saveAndFlush(managedWorkflow);
        }
        return workflow;
    }

    @Override
    public boolean deleteWorkflow(int id) {
        Optional<Workflow> optWorkflow = workflowRepo.findById(id);
        Workflow managedWorkflow;
        if (optWorkflow.isPresent()) {
            managedWorkflow = optWorkflow.get();
            managedWorkflow.setEnabled(false);
            workflowRepo.saveAndFlush(managedWorkflow);
            return true;
        }
        return false;
    }

    @Override
    public List<User> findUsersByWorkflowId(int id) {
        if (!workflowRepo.existsById(id)) {
            return null;
        }
        return userRepo.findUserByWorkflowsId(id);
    }

    @Override
    public List<Document> findDocumentsByWorkflowId(int id) {
        if (!workflowRepo.existsById(id)) {
            return null;
        }
        return documentRepo.findByWorkflowId(id);
    }
}
