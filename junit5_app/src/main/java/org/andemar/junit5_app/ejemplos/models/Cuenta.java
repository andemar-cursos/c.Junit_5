package org.andemar.junit5_app.ejemplos.models;

import org.andemar.junit5_app.ejemplos.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;
import java.util.Objects;

public class Cuenta {
    private String persona;
    private BigDecimal saldo;
    private Banco banco;

    public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void debito(BigDecimal monto) {
        BigDecimal nuevoSaldo = saldo.subtract(monto);

        if (nuevoSaldo.compareTo(BigDecimal.ZERO)  < 0) throw new DineroInsuficienteException("Dinero Insuficiente");

        this.saldo = nuevoSaldo;
    }

    public void credito(BigDecimal monto) {
        saldo = saldo.add(monto);
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta account = (Cuenta) o;
        return Objects.equals(persona, account.persona) && Objects.equals(saldo, account.saldo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persona, saldo);
    }
}
