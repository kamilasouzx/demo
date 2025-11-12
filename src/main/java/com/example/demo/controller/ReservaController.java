package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ReservaDTO;
import com.example.demo.entity.Reserva;
import com.example.demo.service.ReservaService;

@RestController
@RequestMapping("/reservas")
public class ReservaController extends BaseController<ReservaDTO> {

    private ReservaService service;

    protected ReservaController(ReservaService service) {
        super(service);
    }

    @GetMapping("/por-data/{dataInicio}/{dataFim}")
    public List<ReservaDTO> reservaPorData(
            @PathVariable("dataInicio") String dataInicio,
            @PathVariable("dataFim") String dataFim) {
        return service.listaPorData(dataInicio, dataFim);
    }

    @GetMapping("/por-ambiente/{id}")
    public List<ReservaDTO> reservaPorAmbiente(@PathVariable("ambienteId") Long ambienteId) {
        return service.listaPorAmbiente(ambienteId);
    }

    //listar por nome
    @GetMapping("/por-nome/{nome}")
    public List<ReservaDTO> reservaPorNome(@PathVariable("nome") String nome) {
        return service.listaPorNome(nome);
    }
}

