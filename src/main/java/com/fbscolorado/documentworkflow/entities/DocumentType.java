package com.fbscolorado.documentworkflow.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "document_type")
@Getter @Setter @NoArgsConstructor
public class DocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;

    @OneToMany(mappedBy = "documentType")
    private List<Document> documents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentType that = (DocumentType) o;
        return id == that.id && type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "DocumentType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    public void addDocument (Document document) {
        if (documents == null) documents = new ArrayList<>();
        if (!documents.contains(document)) {
            documents.add(document);
            if (document.getDocumentType() != null) {
                document.getDocumentType().getDocuments().remove(document);
            }
            document.setDocumentType(this);
        }
    }

    public void removeDocument (Document document) {
        document.setDocumentType(null);
        if (documents != null) documents.remove(document);
    }
}
