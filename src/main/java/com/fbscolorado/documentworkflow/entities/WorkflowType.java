package com.fbscolorado.documentworkflow.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "workflow_type")
@Getter @Setter @NoArgsConstructor
public class WorkflowType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;

    @OneToMany(mappedBy = "workflow_type")
    private List<Workflow> workflows;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkflowType that = (WorkflowType) o;
        return id == that.id && type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "WorkflowType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    public void addWorkflow(Workflow workflow) {
        if (workflows == null) workflows = new ArrayList<>();
        if (!workflows.contains(workflow)) {
            workflows.add(workflow);
            if (workflow.getWorkflowType() != null) {
                workflow.getWorkflowType().getWorkflows().remove(workflow);
            }
            workflow.setWorkflowType(this);
        }
    }

    public void removeWorkflow(Workflow workflow) {
        workflow.setWorkflowType(null);
        if (workflows != null) workflows.remove(workflow);
    }
}
