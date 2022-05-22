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
}