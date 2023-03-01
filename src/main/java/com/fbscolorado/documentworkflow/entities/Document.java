package com.fbscolorado.documentworkflow.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter @Setter @NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String link;
    private boolean enabled;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
    @ManyToOne
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;
    @ManyToOne
    @JoinColumn(name = "workflow_id")
    private Workflow workflow;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return id == document.id && enabled == document.enabled && link.equals(document.link) && created.equals(document.created) && updated.equals(document.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, enabled, created, updated);
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", active=" + enabled +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
