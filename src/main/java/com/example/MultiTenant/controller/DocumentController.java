package com.example.MultiTenant.controller;

import com.example.MultiTenant.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @GetMapping("")
    public ResponseEntity<?> getDocumentos() {
        return ResponseEntity.ok(documentService.getAll());
    }
}
