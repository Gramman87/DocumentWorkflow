package com.fbscolorado.documentworkflow.controllers;

import com.fbscolorado.documentworkflow.entities.Document;
import com.fbscolorado.documentworkflow.entities.User;
import com.fbscolorado.documentworkflow.entities.Workflow;
import com.fbscolorado.documentworkflow.services.WorkflowService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class WorkflowController {
    @Autowired
    private WorkflowService workflowService;

    @GetMapping("workflows/{id}")
    public Workflow showWorkflow (@PathVariable Integer id) {
        return workflowService.findById(id);
    }

    @PostMapping("workflows")
    public Workflow createWorkflow (@RequestBody Workflow workflow, HttpServletResponse res, HttpServletRequest req) {
        try {
            workflowService.createWorkflow(workflow);
            res.setStatus(201);
            StringBuffer url = req.getRequestURL();
            url.append("/").append(workflow.getId());
            res.setHeader("Location", url.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("INVALID ENTRY FOR NEW Workflow");
            workflow = null;
        }
        return workflow;
    }

    @PutMapping("workflows/{id}")
    public Workflow updateWorkflow (@RequestBody Workflow workflow, @PathVariable Integer id, HttpServletResponse res) {
        try {
            workflowService.updateWorkflow(id, workflow);
            if (workflow == null) {
                res.setStatus(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(400);
            workflow = null;
        }
        return workflow;
    }

    @DeleteMapping("workflows/{id}")
    public void deleteWorkflow (@PathVariable Integer id, HttpServletResponse res) {
        try {
            if (workflowService.deleteWorkflow(id)) {
                res.setStatus(204);
            } else {
                res.setStatus(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(400);
        }
    }

    @GetMapping("workflows/{id}/users")
    public List<User> indexUsers (@PathVariable Integer id, HttpServletResponse res) {
        List<User> users = workflowService.findUsersByWorkflowId(id);
        if (users == null) {
            res.setStatus(404);
        }
        return users;
    }

    @GetMapping("workflows/{id}/documents")
    public List<Document> indexDocuments (@PathVariable Integer id, HttpServletResponse res) {
        List<Document> documents = workflowService.findDocumentsByWorkflowId(id);
        if (documents == null) {
            res.setStatus(404);
        }
        return documents;
    }
}
