package com.poc.MultiTenant.model.dtos;

import com.poc.MultiTenant.model.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter

public class DocumentDto {
    private String nomeDocumento;
    private Date dataDocumento;
    private String nomeEmpresa;

    public DocumentDto(Document document) {
        this.dataDocumento = document.getDataDocumento();
        this.nomeDocumento = document.getNomeDocumento();
        this.nomeEmpresa = document.getNomeEmpresa();
    }
}
