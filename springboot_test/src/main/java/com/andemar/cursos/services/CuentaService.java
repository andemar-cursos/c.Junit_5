package com.andemar.cursos.services;

import com.andemar.cursos.models.Cuenta;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaService {
    List<Cuenta> findAll();
    Cuenta findById(Long id);
    Cuenta save(Cuenta cuenta);
    void deleteById(Long id);
    int revisarTotalTransferencias(Long bancoId);
    BigDecimal revisarSaldo(Long cuentaId);
    void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto,
                    Long bancoId);
}
