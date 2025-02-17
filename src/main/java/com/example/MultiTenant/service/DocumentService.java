package com.example.MultiTenant.service;

import com.example.MultiTenant.model.Document;
import com.example.MultiTenant.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    Logger logger = LoggerFactory.getLogger(DocumentService.class);

    @Autowired
    DocumentRepository documentRepository;

    public List<Document> getAll() {
        List<Document> documents = documentRepository.findAll();
        logger.info("Documentos encontrados: {}", documents);
        return documents;
    }
}
