package com.fbscolorado.documentworkflow.services;

import com.fbscolorado.documentworkflow.entities.Document;

public interface DocumentService {
    public Document findById(int id);
    public Document createDocument(Document document);
    public Document updateDocument(int id, Document document);
    public boolean deleteDocument(int id);
}
