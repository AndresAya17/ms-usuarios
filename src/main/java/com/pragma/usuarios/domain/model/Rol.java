package com.pragma.usuarios.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class Rol {
    private Long id;
    private String name;
    private String description;

    public Rol(Long id) {
        this.id = id;
    }
}
