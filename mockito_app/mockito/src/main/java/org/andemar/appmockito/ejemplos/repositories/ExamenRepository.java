package org.andemar.appmockito.ejemplos.repositories;

import org.andemar.appmockito.ejemplos.models.Examen;

import java.util.List;

public interface ExamenRepository {

    Examen guardar(Examen examen);

    List<Examen> findAll();
}
