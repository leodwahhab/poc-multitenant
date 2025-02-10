package com.poc.MultiTenant.service;

import com.poc.MultiTenant.model.Document;
import com.poc.MultiTenant.model.dtos.DocumentDto;
import com.poc.MultiTenant.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;

    public List<Document> fetchData() {
        return documentRepository.findAll();
    }
}
