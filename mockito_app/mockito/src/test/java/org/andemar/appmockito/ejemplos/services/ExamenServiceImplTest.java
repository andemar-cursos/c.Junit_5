package org.andemar.appmockito.ejemplos.services;

import org.andemar.appmockito.ejemplos.models.Examen;
import org.andemar.appmockito.ejemplos.repositories.ExamenRepository;
import org.andemar.appmockito.ejemplos.repositories.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ExamenServiceImplTest {

    ExamenRepository repository;
    PreguntaRepository preguntaRepository;
    ExamenService service;

    @BeforeEach
    void setUp() {
        repository = mock(ExamenRepository.class);
        preguntaRepository = mock(PreguntaRepository.class);
        service = new ExamenServiceImpl(repository, preguntaRepository);
    }

    @Test
    void findExamenPorNombre() {
        List<Examen> datos = Arrays.asList(
                new Examen(5L, "Matematicas"),
                new Examen(6L, "Lenguaje"),
                new Examen(7L, "Ciencias")
        );
        when(repository.findAll()).thenReturn(datos);

        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");
        assertTrue(examen.isPresent());
        assertEquals(5, examen.orElseThrow().getId());
        assertEquals("Matematicas", examen.orElseThrow().getNombre());
    }

    @Test
    void findExamenPorNombreListaVacia() {
        List<Examen> datos = Collections.emptyList();
        when(repository.findAll()).thenReturn(datos);

        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");
        assertFalse(examen.isPresent());
    }
}