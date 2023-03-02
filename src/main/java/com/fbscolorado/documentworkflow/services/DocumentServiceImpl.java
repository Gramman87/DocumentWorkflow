package com.fbscolorado.documentworkflow.services;

import com.fbscolorado.documentworkflow.entities.Document;
import com.fbscolorado.documentworkflow.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepo;
    @Override
    public Document findById(int id) {
        Optional<Document> optDocument = documentRepo.findById(id);
        return optDocument.orElse(null);
    }

    @Override
    public Document createDocument(Document document) {
        return documentRepo.saveAndFlush(document);
    }

    @Override
    public Document updateDocument(int id, Document document) {
        Optional<Document> optDocument = documentRepo.findById(id);
        Document managedDocument;
        if (optDocument.isPresent()) {
            managedDocument = optDocument.get();
            managedDocument.setLink(document.getLink());
            managedDocument.setDocumentType(document.getDocumentType());
            documentRepo.saveAndFlush(managedDocument);
        }
        return document;
    }

    @Override
    public boolean deleteDocument(int id) {
        Optional<Document> optDocument = documentRepo.findById(id);
        Document managedDocument;
        if (optDocument.isPresent()) {
            managedDocument = optDocument.get();
            managedDocument.setEnabled(false);
            documentRepo.saveAndFlush(managedDocument);
            return true;
        }
        return false;
    }
}