package org.andemar.junit5_app.ejemplos.exceptions;

public class DineroInsuficienteException extends RuntimeException {

    public DineroInsuficienteException(String dineroInsuficiente) {
        super(dineroInsuficiente);
    }
}
