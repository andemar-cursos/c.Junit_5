package org.andemar.junit5_app.ejemplos;

import org.andemar.junit5_app.ejemplos.exceptions.DineroInsuficienteException;
import org.andemar.junit5_app.ejemplos.models.Banco;
import org.andemar.junit5_app.ejemplos.models.Cuenta;
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

        // Si se envia el string el valor se creara en memoria, al enviarse
        // la lambda se ejecuta solo si la prueba falla
        assertNotNull(account, () -> "La cuenta no puede ser nula");
        assertEquals(expected, actual, () -> "El nombre de la cuenta no es correcto");
        assertTrue(actual.equals(expected), () -> "nombre cuenta esperada deber ser igual a nombre cuenta real");
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

    @Test
    void testTransferirDineroCuentas() {
        Cuenta cuenta1 = new Cuenta("Andemar", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Mashiro", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Banco de la Tierra");
        banco.transferir(cuenta2, cuenta1, new BigDecimal("500"));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }


    @Test
    void testRelacionesBancoCuentas() {
        Cuenta cuenta1 = new Cuenta("Andemar", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Mashiro", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        String name = "Banco de la Tierra";
        banco.setNombre(name);
        banco.transferir(cuenta2, cuenta1, new BigDecimal("500"));

        assertAll(
                () -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString()),
                () -> assertEquals("3000", cuenta1.getSaldo().toPlainString()),
                () -> assertEquals(2, banco.getCuentas().size()),
                () -> assertEquals(name, cuenta1.getBanco().getNombre()),
                () -> assertEquals("Andemar", banco.getCuentas().stream().filter(c -> c.getPersona().equals("Andemar")).findFirst().get().getPersona()),
                () -> assertTrue(banco.getCuentas().stream().anyMatch(c -> c.getPersona().equals("Andemar")))
        );

    }
}