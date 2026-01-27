package com.pragma.usuarios.infrastructure.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@ContextConfiguration(classes = OpenApiConfiguration.class)
@TestPropertySource(properties = {
        "appdescription=API de Users para pruebas",
        "appversion=1.0.0-test"
})
public class OpenApiConfigurationTest {

    @Autowired
    private OpenAPI openAPI;

    @Test
    void deberiaCrearElBeanOpenAPIConLaInformacionConfigurada() {
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());

        assertEquals("Hexagonal usuario API", openAPI.getInfo().getTitle());
        assertEquals("1.0.0-test", openAPI.getInfo().getVersion());
        assertEquals("API de Users para pruebas", openAPI.getInfo().getDescription());

        assertNotNull(openAPI.getInfo().getLicense());
        assertEquals("Apache 2.0", openAPI.getInfo().getLicense().getName());
    }
}
