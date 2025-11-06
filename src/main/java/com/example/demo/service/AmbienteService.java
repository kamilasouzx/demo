package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.DTO.AmbienteDTO;
import com.example.demo.entity.Ambiente;
import com.example.demo.repository.AmbienteRepository;

@Service
public class AmbienteService extends BaseService<Ambiente, AmbienteDTO> {

    private AmbienteRepository repository;

    protected AmbienteService(AmbienteRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public void delete(Long id){
        boolean reservado = repository.temReservaFutura(id);
        if(reservado){
            throw new IllegalStateException("Ambiente possui reservas futuras e não pode ser excluído.");
        }
        super.delete(id);
    }
    
}
