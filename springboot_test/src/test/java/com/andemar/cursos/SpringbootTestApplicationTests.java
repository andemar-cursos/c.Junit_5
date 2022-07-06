package com.andemar.cursos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.andemar.cursos.data.Datos;
import com.andemar.cursos.impls.CuentaServiceImpl;
import com.andemar.cursos.repositories.BancoRepository;
import com.andemar.cursos.repositories.CuentaRepository;
import com.andemar.cursos.services.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class SpringbootTestApplicationTests {

    CuentaRepository cuentaRepository;
    BancoRepository bancoRepository;

    CuentaService service;

    @BeforeEach
    void setUp() {
        cuentaRepository = mock(CuentaRepository.class);
        bancoRepository = mock(BancoRepository.class);
        service = new CuentaServiceImpl(cuentaRepository, bancoRepository);
    }

    @Test
    void contextLoads() {
        when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
        when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
        when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);

        BigDecimal saldoOrigen = service.revisarSaldo(1L);
        BigDecimal saldoDestino = service.revisarSaldo(2L);
        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        service.transferir(1L, 2L, new BigDecimal("100"), 1L);
        saldoOrigen = service.revisarSaldo(1L);
        saldoDestino = service.revisarSaldo(2L);
        assertEquals("900", saldoOrigen.toPlainString());
        assertEquals("2100", saldoDestino.toPlainString());
    }

}
