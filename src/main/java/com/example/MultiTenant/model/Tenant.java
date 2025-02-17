package com.example.MultiTenant.model;

import javax.persistence.*;

import lombok.Getter;

import java.util.List;

@Getter
@Entity(name = "tenants")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tenantName;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;
}
