package com.andemar.cursos.repositories;

import com.andemar.cursos.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
//    List<Cuenta> findAll();
//    Cuenta findById(Long id);
//    void update(Cuenta cuenta);
}
