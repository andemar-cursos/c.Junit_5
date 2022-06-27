package org.andemar.appmockito.ejemplos.repositories;

import java.util.List;

public interface PreguntaRepository {

    void guardarVarias(List<String> preguntas);

    List<String> findPreguntasPorExamenId(Long examenId);
}
