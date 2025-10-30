package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.ReservaDTO;
import com.example.demo.entity.Reserva;
import com.example.demo.repository.ReservaRepository;

@Service
public class ReservaService extends BaseService<Reserva, ReservaDTO> {

    private ReservaRepository repository;

    protected ReservaService(ReservaRepository repository) {
        super(repository);
        this.repository = repository;
    }
    
}
