package com.fbscolorado.documentworkflow.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter @NoArgsConstructor
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "current_user")
    private int currentUser;
    @Column(name = "next_user")
    private int nextUser;
    private boolean active;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
    @ManyToOne
    @JoinColumn(name = "workflow_type_id")
    private WorkflowType workflowType;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_workflow", joinColumns = @JoinColumn(name = "workflow_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
    @OneToMany(mappedBy = "document_id")
    private List<Document> documents;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workflow workflow = (Workflow) o;
        return id == workflow.id && active == workflow.active && created.equals(workflow.created) && updated.equals(workflow.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active, created, updated);
    }

    @Override
    public String toString() {
        return "Workflow{" +
                "id=" + id +
                ", active=" + active +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }

    public void addUser (User user) {
        if (users == null) users = new ArrayList<>();
        if (!users.contains(user)) {
            users.add(user);
            user.addWorkflow(this);
        }
    }

    public void removeUser (User user) {
        if (users != null && users.contains(user)) {
            users.remove(user);
            user.removeWorkflow(this);
        }
    }

    public void addDocument (Document document) {
        if (documents == null) documents = new ArrayList<>();
        if (!documents.contains(document)) {
            documents.add(document);
            if (document.getWorkflow() != null) {
                document.getWorkflow().getDocuments().remove(document);
            }
            document.setWorkflow(this);
        }
    }

    public void removeDocument (Document document) {
        document.setWorkflow(null);
        if (documents != null) {
            documents.remove(document);
        }
    }
}
