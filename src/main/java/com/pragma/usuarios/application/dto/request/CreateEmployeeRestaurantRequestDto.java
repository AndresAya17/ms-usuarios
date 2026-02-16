package com.pragma.usuarios.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateEmployeeRestaurantRequestDto {
    private Long employeeUserId;
    private Long restaurantId;
}
