package com.andemar.cursos.data;

import com.andemar.cursos.models.Banco;
import com.andemar.cursos.models.Cuenta;

import java.math.BigDecimal;

public class Datos {
    public static final Cuenta CUENTA_001 = new Cuenta(1L, "Andemar", new BigDecimal("1000"));
    public static final Cuenta CUENTA_002 = new Cuenta(2L, "Andemar", new BigDecimal("2000"));
    public static final Banco BANCO = new Banco(1L, "El banco financiero", 0);

    public static Cuenta crearCuenta001() {
        return new Cuenta(1L, "Andemar", new BigDecimal("1000"));
    }

    public static Cuenta crearCuenta002() {
        return new Cuenta(2L, "Andemar", new BigDecimal("2000"));
    }

    public static Banco crearBanco001() {
        return new Banco(1L, "El banco financiero", 0);
    }
}
