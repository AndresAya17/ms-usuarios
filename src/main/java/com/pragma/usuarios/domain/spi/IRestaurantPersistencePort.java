package com.pragma.usuarios.domain.spi;

public interface IRestaurantPersistencePort {
    void validateOwner(Long restaurantId, Long userId);
    void createEmployeeRestaurant(Long employeeId, Long restaurantId);
}
