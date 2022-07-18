package com.andemar.cursos.controllers;

import com.andemar.cursos.models.Cuenta;
import com.andemar.cursos.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Cuenta detalle(@PathVariable("id") Long id) {
        return cuentaService.findById(id);
    }

}
