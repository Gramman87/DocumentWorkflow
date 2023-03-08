package com.fbscolorado.documentworkflow.services;

import com.fbscolorado.documentworkflow.entities.Document;
import com.fbscolorado.documentworkflow.entities.User;
import com.fbscolorado.documentworkflow.entities.Workflow;

import java.util.List;

public interface WorkflowService {
    public Workflow findById(int id);
    public Workflow createWorkflow(Workflow workflow);
    public Workflow updateWorkflow(int id, Workflow workflow);
    public boolean deleteWorkflow(int id);
    public List<User> findUsersByWorkflowId(int id);
    public List<Document> findDocumentsByWorkflowId(int id);
}
