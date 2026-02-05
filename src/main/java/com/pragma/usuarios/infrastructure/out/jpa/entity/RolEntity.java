package com.pragma.usuarios.infrastructure.out.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Setter
@Getter
public class RolEntity {
    @Id
    private Long id;
    private String name;
    private String description;
}
