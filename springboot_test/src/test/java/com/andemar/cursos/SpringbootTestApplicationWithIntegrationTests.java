package com.andemar.cursos;

import com.andemar.cursos.exceptions.DineroInsuficienteException;
import com.andemar.cursos.models.Banco;
import com.andemar.cursos.models.Cuenta;
import com.andemar.cursos.repositories.BancoRepository;
import com.andemar.cursos.repositories.CuentaRepository;
import com.andemar.cursos.services.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static com.andemar.cursos.data.Datos.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SpringbootTestApplicationWithIntegrationTests {

    @MockBean
    CuentaRepository cuentaRepository;
    @MockBean
    BancoRepository bancoRepository;

    // Se necesita anotar como @Service para que quede en el contexto de spring como Bean.
    // Al contrario de Mock, con Spring no se necesita la implementación, se puede usar la interfaz.
    @Autowired
    CuentaService service;

    @BeforeEach
    void setUp() {
//        cuentaRepository = mock(CuentaRepository.class);
//        bancoRepository = mock(BancoRepository.class);
//        service = new CuentaServiceImpl(cuentaRepository, bancoRepository);
        // Esto es para reiniciar los valores en cada test, pero es mejor con metodos estaticos que retornen una
        // nueva instancia
//        Datos.CUENTA_001.setSaldo(new BigDecimal("1000"));
//        Datos.CUENTA_002.setSaldo(new BigDecimal("2000"));
//        Datos.BANCO.setTotalTransferencia(0);
    }

    @Test
    void contextLoads() {
//        when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
//        when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
//        when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);
        when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
        when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
        when(bancoRepository.findById(1L)).thenReturn(crearBanco001());

        BigDecimal saldoOrigen = service.revisarSaldo(1L);
        BigDecimal saldoDestino = service.revisarSaldo(2L);
        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        service.transferir(1L, 2L, new BigDecimal("100"), 1L);
        saldoOrigen = service.revisarSaldo(1L);
        saldoDestino = service.revisarSaldo(2L);
        assertEquals("900", saldoOrigen.toPlainString());
        assertEquals("2100", saldoDestino.toPlainString());

        int total = service.revisarTotalTransferencias(1L);
        assertEquals(1, total);

        verify(cuentaRepository, times(3)).findById(1L);
        verify(cuentaRepository, times(3)).findById(2L);
        verify(cuentaRepository, times(2)).save(any(Cuenta.class));
        verify(bancoRepository, times(2)).findById(anyLong());
        verify(bancoRepository).save(any(Banco.class));

        verify(cuentaRepository, times(6)).findById(anyLong());
        verify(cuentaRepository, never()).findAll();
    }

    @Test
    void contextLoads2() {
//        when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
//        when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
//        when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);
        when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
        when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
        when(bancoRepository.findById(1L)).thenReturn(crearBanco001());

        BigDecimal saldoOrigen = service.revisarSaldo(1L);
        BigDecimal saldoDestino = service.revisarSaldo(2L);
        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        assertThrows(DineroInsuficienteException.class, () -> {
            service.transferir(1L, 2L, new BigDecimal("1200"), 1L);
        });

        saldoOrigen = service.revisarSaldo(1L);
        saldoDestino = service.revisarSaldo(2L);
        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        int total = service.revisarTotalTransferencias(1L);
        assertEquals(0, total);

        verify(cuentaRepository, times(3)).findById(1L);
        verify(cuentaRepository, times(2)).findById(2L);
        verify(cuentaRepository, never()).save(any(Cuenta.class));
        verify(bancoRepository).findById(anyLong());
        verify(bancoRepository, never()).save(any(Banco.class));

        verify(cuentaRepository, times(5)).findById(anyLong());
        verify(cuentaRepository, never()).findAll();
    }

    @Test
    void contextLoads3() {
        when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());

        Cuenta cuenta1 = service.findById(1L);
        Cuenta cuenta2 = service.findById(1L);

        assertSame(cuenta1, cuenta2);
        assertTrue(cuenta1 == cuenta2);
        assertEquals("Andemar", cuenta2.getPersona());
        verify(cuentaRepository, times(2)).findById(1L);
    }
}
