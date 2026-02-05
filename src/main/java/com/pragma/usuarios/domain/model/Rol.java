package com.pragma.usuarios.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Rol {
    private Long id;
    private String name;
    private String description;

    public Rol(Long id) {
        this.id = id;
    }
}
