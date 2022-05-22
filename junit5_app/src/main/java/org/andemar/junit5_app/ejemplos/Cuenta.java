package org.andemar.junit5_app.ejemplos;

import java.math.BigDecimal;

public class Cuenta {
    private String Persona;
    private BigDecimal saldo;

    public String getPersona() {
        return Persona;
    }

    public void setPersona(String persona) {
        Persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
