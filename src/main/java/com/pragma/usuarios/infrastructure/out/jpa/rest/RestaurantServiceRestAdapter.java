package com.pragma.usuarios.infrastructure.out.jpa.rest;

import com.pragma.usuarios.application.dto.request.CreateEmployeeRestaurantRequestDto;
import com.pragma.usuarios.domain.exception.DomainException;
import com.pragma.usuarios.domain.exception.ErrorCode;
import com.pragma.usuarios.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestaurantServiceRestAdapter implements IRestaurantPersistencePort {

    private final RestTemplate restTemplate;

    @Value("${plazoleta.service.url}")
    private String plazoletaServiceUrl;

    @Override
    public void validateOwner(Long restaurantId, Long userId) {
        String url = plazoletaServiceUrl +
                "/api/v1/plazoleta/restaurant/{restaurantId}/validate-owner?userId={userId}";

        try {
            restTemplate.getForEntity(
                    url,
                    Void.class,
                    restaurantId,
                    userId
            );
        } catch (HttpClientErrorException.Forbidden e) {
            throw new DomainException(
                    ErrorCode.UNAUTHORIZED,
                    "The user is not the owner of this restaurant"
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new DomainException(
                    ErrorCode.DATA_NOT_FOUND,
                    "Restaurant not found"
            );
        } catch (RestClientException e) {
            throw new DomainException(
                    ErrorCode.EXTERNAL_SERVICE_ERROR,
                    "Error communicating with plazoleta service"
            );
        }
    }

    @Override
    public void createEmployeeRestaurant(Long employeeId, Long restaurantId) {
        String url = plazoletaServiceUrl +
                "/api/v1/plazoleta/restaurant/employeeRestaurant/";

        CreateEmployeeRestaurantRequestDto request =
                new CreateEmployeeRestaurantRequestDto(
                        employeeId,
                        restaurantId
                );

        try {
            restTemplate.postForEntity(url, request, Void.class);
        } catch (RestClientException e) {
            throw new DomainException(ErrorCode.EXTERNAL_SERVICE_ERROR,"Error assigning employee to restaurant");
        }
    }
}
