package org.andemar.junit5_app.ejemplos;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void testAccountName() {
        Account account = new Account("Andemar", new BigDecimal(123));
//        account.setPersona("Andemar");
        String expected = "Andemar";
        String actual = account.getPersona();

        assertEquals(expected, actual);
        assertTrue(actual.equals(expected));
    }

    @Test
    void testSaldoCuenta() {
        Account account = new Account("Andemar", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, account.getSaldo().doubleValue());
        assertFalse(account.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    void testReferenciaDeCuenta() {
        Account cuenta = new Account("Andemar Doe", new BigDecimal("8900.9997"));
        Account cuenta2 = new Account("Andemar Doe", new BigDecimal("8900.9997"));

//        assertNotEquals(cuenta, cuenta2);
        assertEquals(cuenta, cuenta2);
    }
}