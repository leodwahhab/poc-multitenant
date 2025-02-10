package com.poc.MultiTenant.repository;

import com.poc.MultiTenant.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
