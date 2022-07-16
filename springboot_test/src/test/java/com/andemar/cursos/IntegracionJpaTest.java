package com.andemar.cursos;

import com.andemar.cursos.models.Cuenta;
import com.andemar.cursos.repositories.CuentaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IntegracionJpaTest {
    @Autowired
    CuentaRepository cuentaRepository;

    @Test
    void testFindById() {
        Optional<Cuenta> cuenta = cuentaRepository.findById(1L);
        assertTrue(cuenta.isPresent());
        assertEquals("Andemar", cuenta.orElseThrow().getPersona());
    }

    @Test
    void testFindByPersona() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Andemar");
        assertTrue(cuenta.isPresent());
        assertEquals("Andemar", cuenta.orElseThrow().getPersona());
        assertEquals("1000.00", cuenta.orElseThrow().getSaldo().toPlainString());
    }

    @Test
    void testFindByPersonaThrowException() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Andemarr");
        assertThrows(NoSuchElementException.class, cuenta::orElseThrow);
        assertFalse(cuenta.isPresent());
    }

    @Test
    void testFindALl() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        assertFalse(cuentas.isEmpty());
        assertEquals(2, cuentas.size());
    }

    @Test
    void testSave() {
        //Given
        Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal(3000));
        cuentaRepository.save(cuentaPepe);

        //When
//        Cuenta cuenta = cuentaRepository.findByPersona("Pepe").orElseThrow();
        // Tambien se puede usar el resultado de save, ya que es un retorno de la cuenta desde la DB cuando se inserta
        Cuenta cuenta = cuentaRepository.findById(cuentaPepe.getId()).orElseThrow();

        //Then
        assertEquals("Pepe", cuenta.getPersona());
        assertEquals("3000", cuenta.getSaldo().toPlainString());
//        assertEquals(3, cuenta.getId());
    }

    @Test
    void testUpdate() {
        //Given
        Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal(3000));
        cuentaRepository.save(cuentaPepe);

        //When
//        Cuenta cuenta = cuentaRepository.findByPersona("Pepe").orElseThrow();
        // Tambien se puede usar el resultado de save, ya que es un retorno de la cuenta desde la DB cuando se inserta
        Cuenta cuenta = cuentaRepository.findById(cuentaPepe.getId()).orElseThrow();

        //Then
        assertEquals("Pepe", cuenta.getPersona());
        assertEquals("3000", cuenta.getSaldo().toPlainString());
//        assertEquals(3, cuenta.getId());

        //When
        cuenta.setSaldo(new BigDecimal(3800));
        Cuenta cuentaSave = cuentaRepository.save(cuenta);

        //Then
        assertEquals("3800", cuentaSave.getSaldo().toPlainString());

    }

    @Test
    void testDeletePropio() {
        //Given
        Cuenta cuentaDelete = new Cuenta(null, "Delete", new BigDecimal("5000"));
        cuentaRepository.save(cuentaDelete);

        //When
        Optional<Cuenta> cuentaDeleteSave = cuentaRepository.findById(cuentaDelete.getId());
        Optional<Cuenta> cuentaAndemar    = cuentaRepository.findByPersona("Andemar");

        //Then
        assertTrue(cuentaDeleteSave.isPresent());
        assertTrue(cuentaAndemar.isPresent());

        //When
        cuentaRepository.delete(cuentaDeleteSave.orElseThrow());
        cuentaRepository.delete(cuentaAndemar.orElseThrow());

        cuentaDeleteSave = cuentaRepository.findById(cuentaDelete.getId());
        cuentaAndemar    = cuentaRepository.findByPersona("Andemar");

       //Then
        assertFalse(cuentaDeleteSave.isPresent());
        assertFalse(cuentaAndemar.isPresent());
    }

    @Test
    void testDelete() {
        Cuenta cuenta = cuentaRepository.findById(2L).orElseThrow();
        assertEquals("Mashiro", cuenta.getPersona());

        cuentaRepository.delete(cuenta);

        assertThrows(NoSuchElementException.class, () -> {
            cuentaRepository.findByPersona("Mashiro").orElseThrow();
            cuentaRepository.findById(2L).orElseThrow();
        });

        assertEquals(1, cuentaRepository.findAll().size());
    }
}
