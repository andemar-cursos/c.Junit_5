package org.andemar.appmockito.ejemplos.services;

import org.andemar.appmockito.ejemplos.models.Examen;
import org.andemar.appmockito.ejemplos.repositories.ExamenRepository;
import org.andemar.appmockito.ejemplos.repositories.PreguntaRepository;

import java.util.Optional;

public class ExamenServiceImpl implements ExamenService {
     private ExamenRepository examenRepository;
     private PreguntaRepository preguntaRepository;

    public ExamenServiceImpl(ExamenRepository examenRepository, PreguntaRepository preguntaRepository) {
        this.examenRepository = examenRepository;
        this.preguntaRepository = preguntaRepository;
    }

    @Override
    public Optional<Examen> findExamenPorNombre(String nombre) {
        return examenRepository.findAll().stream()
                .filter(examen -> examen.getNombre().equals(nombre)).
                findFirst();
    }

    @Override
    public Examen findExamenPorNombreConPreguntas(String nombre) {
        Optional<Examen> examenOptional = findExamenPorNombre(nombre);
        Examen examen = examenOptional.orElseThrow();

        examen.setPreguntas(
                preguntaRepository.findPreguntasPorExamenId(examen.getId())
        );

        return examen;
    }
}
