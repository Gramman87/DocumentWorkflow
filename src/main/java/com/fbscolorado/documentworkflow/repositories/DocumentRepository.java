package com.fbscolorado.documentworkflow.repositories;

import com.fbscolorado.documentworkflow.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findByWorkflowId(Integer id);
}
