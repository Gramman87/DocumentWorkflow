package com.fbscolorado.documentworkflow.controllers;

import com.fbscolorado.documentworkflow.entities.Document;
import com.fbscolorado.documentworkflow.services.DocumentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping("documents/{id}")
    public Document showDocument (@PathVariable Integer id, HttpServletResponse res) {
        Document document = documentService.findById(id);
        if (document == null) {
            res.setStatus(404);
        }
        return document;
    }

    @PostMapping("documents")
    public Document createDocument (@RequestBody Document document, HttpServletResponse res, HttpServletRequest req) {
        try {
            documentService.createDocument(document);
            res.setStatus(201);
            StringBuffer url = req.getRequestURL();
            url.append("/").append(document.getId());
            res.setHeader("Location", url.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("INVALID ENTRY FOR Document CREATION");
            document = null;
        }
        return document;
    }

    @PutMapping("document/{id}")
    public Document updateDocument (@RequestBody Document document, @PathVariable Integer id, HttpServletResponse res) {
        try {
            documentService.updateDocument(id, document);
            if (document == null) {
                res.setStatus(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(400);
            document = null;
        }
        return document;
    }

    @DeleteMapping("documents/{id}")
    public void deleteDocument (@PathVariable Integer id, HttpServletResponse res) {
        try {
            if (documentService.deleteDocument(id)) {
                res.setStatus(204);
            } else {
                res.setStatus(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(400);
        }
    }
}
