package com.poc.MultiTenant.model;

import com.poc.MultiTenant.model.dtos.DocumentDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeDocumento;
    private Date dataDocumento;
    private String nomeEmpresa;

    public Document(DocumentDto documentDto) {
        this.dataDocumento = documentDto.getDataDocumento();
        this.nomeDocumento = documentDto.getNomeDocumento();
        this.nomeEmpresa = documentDto.getNomeEmpresa();
    }
}
