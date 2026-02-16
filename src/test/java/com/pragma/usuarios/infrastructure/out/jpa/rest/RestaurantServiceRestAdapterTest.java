package com.pragma.usuarios.infrastructure.out.jpa.rest;

import com.pragma.usuarios.application.dto.request.CreateEmployeeRestaurantRequestDto;
import com.pragma.usuarios.domain.exception.DomainException;
import com.pragma.usuarios.domain.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceRestAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestaurantServiceRestAdapter adapter;

    private final String baseUrl = "http://localhost:8081";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(adapter, "plazoletaServiceUrl", baseUrl);
    }

    @Test
    void shouldValidateOwnerSuccessfully() {

        Long restaurantId = 1L;
        Long userId = 10L;

        String expectedUrl = baseUrl +
                "/api/v1/plazoleta/restaurant/{restaurantId}/validate-owner?userId={userId}";

        when(restTemplate.getForEntity(
                eq(expectedUrl),
                eq(Void.class),
                eq(restaurantId),
                eq(userId)
        )).thenReturn(null);

        assertDoesNotThrow(() ->
                adapter.validateOwner(restaurantId, userId)
        );

        verify(restTemplate).getForEntity(
                eq(expectedUrl),
                eq(Void.class),
                eq(restaurantId),
                eq(userId)
        );
    }

    @Test
    void shouldThrowUnauthorizedWhenForbidden() {

        Long restaurantId = 1L;
        Long userId = 10L;

        when(restTemplate.getForEntity(
                anyString(),
                eq(Void.class),
                eq(restaurantId),
                eq(userId)
        )).thenThrow(HttpClientErrorException.Forbidden.class);

        DomainException ex = assertThrows(
                DomainException.class,
                () -> adapter.validateOwner(restaurantId, userId)
        );

        assertEquals(ErrorCode.UNAUTHORIZED, ex.getErrorCode());
        assertEquals("The user is not the owner of this restaurant", ex.getMessage());
    }

    @Test
    void shouldThrowDataNotFoundWhenRestaurantNotFound() {

        Long restaurantId = 1L;
        Long userId = 10L;

        when(restTemplate.getForEntity(
                anyString(),
                eq(Void.class),
                eq(restaurantId),
                eq(userId)
        )).thenThrow(HttpClientErrorException.NotFound.class);

        DomainException ex = assertThrows(
                DomainException.class,
                () -> adapter.validateOwner(restaurantId, userId)
        );

        assertEquals(ErrorCode.DATA_NOT_FOUND, ex.getErrorCode());
        assertEquals("Restaurant not found", ex.getMessage());
    }

    @Test
    void shouldThrowExternalServiceErrorOnOtherClientError() {

        Long restaurantId = 1L;
        Long userId = 10L;

        when(restTemplate.getForEntity(
                anyString(),
                eq(Void.class),
                eq(restaurantId),
                eq(userId)
        )).thenThrow(new RestClientException("Connection error"));

        DomainException ex = assertThrows(
                DomainException.class,
                () -> adapter.validateOwner(restaurantId, userId)
        );

        assertEquals(ErrorCode.EXTERNAL_SERVICE_ERROR, ex.getErrorCode());
        assertEquals("Error communicating with plazoleta service", ex.getMessage());
    }

    @Test
    void shouldCreateEmployeeRestaurantSuccessfully() {

        Long employeeId = 5L;
        Long restaurantId = 2L;

        String expectedUrl = baseUrl +
                "/api/v1/plazoleta/restaurant/employeeRestaurant/";

        when(restTemplate.postForEntity(
                eq(expectedUrl),
                any(CreateEmployeeRestaurantRequestDto.class),
                eq(Void.class)
        )).thenReturn(null);

        assertDoesNotThrow(() ->
                adapter.createEmployeeRestaurant(employeeId, restaurantId)
        );

        verify(restTemplate).postForEntity(
                eq(expectedUrl),
                argThat((CreateEmployeeRestaurantRequestDto request) ->
                        request.getEmployeeUserId().equals(employeeId) &&
                                request.getRestaurantId().equals(restaurantId)
                ),
                eq(Void.class)
        );
    }

    @Test
    void shouldThrowExternalServiceErrorWhenRestCallFails() {

        Long employeeId = 5L;
        Long restaurantId = 2L;

        when(restTemplate.postForEntity(
                anyString(),
                any(CreateEmployeeRestaurantRequestDto.class),
                eq(Void.class)
        )).thenThrow(new RestClientException("Connection error"));

        DomainException ex = assertThrows(
                DomainException.class,
                () -> adapter.createEmployeeRestaurant(employeeId, restaurantId)
        );

        assertEquals(ErrorCode.EXTERNAL_SERVICE_ERROR, ex.getErrorCode());
        assertEquals("Error assigning employee to restaurant", ex.getMessage());
    }
}
