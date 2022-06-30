package org.andemar.appmockito.ejemplos.repositories;

import org.andemar.appmockito.ejemplos.models.Datos;

import java.util.List;

public class PreguntaRepositoryImpl implements PreguntaRepository {

    @Override
    public void guardarVarias(List<String> preguntas) {
        System.out.println("PreguntaRepositoryImpl.guardarVarias");
    }

    @Override
    public List<String> findPreguntasPorExamenId(Long examenId) {
        System.out.println("PreguntaRepositoryImpl.findPreguntasPorExamenId");
        return Datos.PREGUNTAS;
    }
}
