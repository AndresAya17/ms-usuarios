package com.pragma.usuarios.domain.model;

import com.pragma.usuarios.domain.exception.UnderageUserException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String DocumentNumber;
    private String PhoneNumber;
    private LocalDate BirthDate;
    private String email;
    private String password;
    private Rol rol;

    public void validateIsAdult() {
        if (Period.between(BirthDate, LocalDate.now()).getYears() < 18) {
            throw new UnderageUserException();
        }
    }

}
