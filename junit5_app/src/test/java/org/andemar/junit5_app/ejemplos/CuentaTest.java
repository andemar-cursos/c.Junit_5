package org.andemar.junit5_app.ejemplos;

import org.andemar.junit5_app.ejemplos.models.Cuenta;
import org.andemar.junit5_app.ejemplos.models.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void testAccountName() {
        Cuenta account = new Cuenta("Andemar", new BigDecimal(123));
//        account.setPersona("Andemar");
        String expected = "Andemar";
        String actual = account.getPersona();

        assertEquals(expected, actual);
        assertTrue(actual.equals(expected));
    }

    @Test
    void testSaldoCuenta() {
        Cuenta account = new Cuenta("Andemar", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, account.getSaldo().doubleValue());
        assertFalse(account.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    void testReferenciaDeCuenta() {
        Cuenta cuenta = new Cuenta("Andemar Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("Andemar Doe", new BigDecimal("8900.9997"));

//        assertNotEquals(cuenta, cuenta2);
        assertEquals(cuenta, cuenta2);
    }

    @Test
    void testDebitoAccount() {
        Cuenta cuenta = new Cuenta("Andemar", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testCreditoAccount() {
        Cuenta cuenta = new Cuenta("Andemar", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("Andemar", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(DineroInsuficienteException.class, ()-> {
           cuenta.debito(new BigDecimal("1500"));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);
    }
}