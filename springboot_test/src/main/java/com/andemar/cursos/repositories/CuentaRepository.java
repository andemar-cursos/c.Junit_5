package com.andemar.cursos.repositories;

import com.andemar.cursos.models.Cuenta;

import java.util.List;

public interface CuentaRepository {
    List<Cuenta> findAll();
    Cuenta findById();
    void update(Cuenta cuenta);
}
