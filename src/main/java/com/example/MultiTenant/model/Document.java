package com.example.MultiTenant.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "documents")
@ToString
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id")
    private Integer id;
    @JsonProperty(value = "descricao")
    private String descricao;
}
