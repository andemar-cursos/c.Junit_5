package org.andemar.appmockito.ejemplos.services;

import org.andemar.appmockito.ejemplos.models.Examen;

import java.util.Arrays;
import java.util.List;

public class Datos {

    public final static List<Examen> EXAMENES = Arrays.asList(
            new Examen(5L, "Matematicas"),
            new Examen(6L, "Lenguaje"),
            new Examen(7L, "Ciencias")
    );

    public final static List<Examen> EXAMENES_ID_NULL = Arrays.asList(
            new Examen(null, "Matematicas"),
            new Examen(null, "Lenguaje"),
            new Examen(null, "Ciencias")
    );

    public final static List<String> PREGUNTAS = Arrays.asList(
            "Aritmetica",
            "integrales",
            "derivadas",
            "trigonometria",
            "geometria"
    );

    public final static Examen EXAMEN = new Examen(null, "Fisica");
}
