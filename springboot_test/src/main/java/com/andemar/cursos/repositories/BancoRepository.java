package com.andemar.cursos.repositories;

import com.andemar.cursos.models.Banco;

import java.util.List;

public interface BancoRepository {
    List<Banco> findAll();
    Banco findById(Long id);
    void update(Banco banco);
}
