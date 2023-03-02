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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    private String role;
    private boolean enabled;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;

    @ManyToMany(mappedBy = "users")
    private List<Workflow> workflows;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && enabled == user.enabled && email.equals(user.email) && password.equals(user.password) && role.equals(user.role) && created.equals(user.created) && updated.equals(user.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, role, enabled, created, updated);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", active=" + enabled +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }

    public void addWorkflow (Workflow workflow) {
        if (workflows == null) workflows = new ArrayList<>();
        if (!workflows.contains(workflow)) {
            workflows.add(workflow);
            workflow.addUser(this);
        }
    }

    public void removeWorkflow (Workflow workflow) {
        if (workflows != null && workflows.contains(workflow)) {
            workflows.remove(workflow);
            workflow.removeUser(this);
        }
    }
}
