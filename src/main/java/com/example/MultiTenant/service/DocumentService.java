package com.example.MultiTenant.service;

import com.example.MultiTenant.model.Document;
import com.example.MultiTenant.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;

    public List<Document> getAll() {
        return documentRepository.findAll();
    }
}
