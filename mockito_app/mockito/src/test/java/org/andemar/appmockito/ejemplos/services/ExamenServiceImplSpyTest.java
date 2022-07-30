package org.andemar.appmockito.ejemplos.services;

import org.andemar.appmockito.ejemplos.models.Examen;
import org.andemar.appmockito.ejemplos.repositories.ExamenRepository;
import org.andemar.appmockito.ejemplos.repositories.ExamenRepositoryImpl;
import org.andemar.appmockito.ejemplos.repositories.PreguntaRepository;
import org.andemar.appmockito.ejemplos.repositories.PreguntaRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplSpyTest {

    @Spy
    ExamenRepositoryImpl repository;

    @Spy
    PreguntaRepositoryImpl preguntaRepository;

    @InjectMocks
    ExamenServiceImpl service;

    @Test
    void testSpy() {
//        ExamenRepository examenRepository = mock(ExamenRepository.class);
        ExamenRepository examenRepository = spy(ExamenRepositoryImpl.class);
        PreguntaRepository preguntaRepository = spy(PreguntaRepositoryImpl.class);
        ExamenService examenService = new ExamenServiceImpl(examenRepository, preguntaRepository);

        // 1. Con Mock pero se ejecuta el metodo al "almacenar" el when.
//        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        // 2. Se hace con doAnswer si se quiere agregar alguna logica dentro del retorno
//        doAnswer(invocation -> {
//            return Datos.PREGUNTAS;
//        }).when(preguntaRepository).findPreguntasPorExamenId(anyLong());
        // 3. Como los datos a retornar ya estan listo, con doReturn se indican y listo
        doReturn(Datos.PREGUNTAS).when(preguntaRepository).findPreguntasPorExamenId(anyLong());


        Examen examen = examenService.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(5, examen.getId());
        assertEquals("Matematicas", examen.getNombre());
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Aritmetica"));

        verify(examenRepository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }
}