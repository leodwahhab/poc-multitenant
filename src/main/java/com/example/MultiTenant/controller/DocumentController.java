package com.example.MultiTenant.controller;

import com.example.MultiTenant.model.Document;
import com.example.MultiTenant.service.DocumentService;
import com.example.MultiTenant.tenant.TenantRoutingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {
    Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    DocumentService documentService;

    @GetMapping("")
    public ResponseEntity<?> getDocumentos() {
        List<Document> documents = documentService.getAll();

        logger.info("Documentos de resposta: {}", documents);

        return ResponseEntity.ok().body(documents);
    }
}
