package com.andemar.cursos.controllers;

import com.andemar.cursos.models.Cuenta;
import com.andemar.cursos.models.DTO.TransaccionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CuentaControllerWebTestClientTests {

    private ObjectMapper mapper;

    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testTransferir() throws JsonProcessingException {
        // Given
        TransaccionDTO dto = new TransaccionDTO();
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setBancoId(1L);
        dto.setMonto(new BigDecimal("100"));

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("mensaje", "Transferencia realizada con exito");
        response.put("transaccion", dto);

        // When
        client.post().uri("/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                // Then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(respuesta -> {
                    try {
//                        String jsonStr = respuesta.getResponseBody();
//                        JsonNode json = mapper.readTree(jsonStr);
                        JsonNode json = mapper.readTree(respuesta.getResponseBody());
                        assertEquals("Transferencia realizada con exito", json.path("mensaje").asText());
                        assertEquals(1L, json.path("transaccion").path("cuentaOrigenId").asLong());
                        assertEquals(LocalDate.now().toString(), json.path("date").asText());
                        assertEquals("100", json.path("transaccion").path("monto").asText());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .jsonPath("$.mensaje").isNotEmpty()
                .jsonPath("$.mensaje").value(is("Transferencia realizada con exito"))
                .jsonPath("$.mensaje").value(valor -> assertEquals("Transferencia realizada con exito", valor))
                .jsonPath("$.mensaje").isEqualTo("Transferencia realizada con exito")
                .jsonPath("$.transaccion.cuentaOrigenId").isEqualTo(dto.getCuentaOrigenId())
                .jsonPath("$.date").isEqualTo(LocalDate.now().toString())
                .json(mapper.writeValueAsString(response));
    }

    @Test
    @Order(2)
    void testDetalle() throws JsonProcessingException {
        Cuenta cuenta = new Cuenta(1L, "Andemar", new BigDecimal("900"));
        client.get().uri("/api/cuentas/1").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.persona").isEqualTo("Andemar")
                .jsonPath("$.saldo").isEqualTo(900)
                .json(mapper.writeValueAsString(cuenta));
    }

    @Test
    @Order(3)
    void testDetalle2() {
        client.get().uri("/api/cuentas/1").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Cuenta.class)
                .consumeWith(resp -> {
                   Cuenta cuenta = resp.getResponseBody();
                   assertEquals("Andemar", cuenta.getPersona());
                   assertEquals("900.00", cuenta.getSaldo().toPlainString());
                });
    }
}