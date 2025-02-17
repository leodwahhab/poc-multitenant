package com.example.MultiTenant.repository;

import com.example.MultiTenant.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
}
