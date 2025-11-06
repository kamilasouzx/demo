package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.DTO.ReservaDTO;
import com.example.demo.entity.Ambiente;
import com.example.demo.entity.Reserva;
import com.example.demo.repository.AmbienteRepository;
import com.example.demo.repository.ReservaRepository;

@Service
public class ReservaService extends BaseService<Reserva, ReservaDTO> {

    private ReservaRepository repository;

    @Autowired
    AmbienteRepository ambienteRepository;

    protected ReservaService(ReservaRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public ReservaDTO create(ReservaDTO dto) {
        // verficar se o ambiente existe
        Ambiente ambiente = ambienteRepository.findById(dto.getAmbiente().getId())
                .orElseThrow(() -> new IllegalStateException("Ambiente não existe."));

        // verificar se o ambiente está ativo
        if (!ambiente.isAtivo()) {
            throw new IllegalStateException("Ambiente não está ativo.");
        }
        // verificar se o ambiente tem horarios sobrepostos
        boolean disponivel = repository.ambienteDisponivel(ambiente.getId(), dto.getDataInicio(), dto.getDataFim());

        if (!disponivel) {
            throw new IllegalStateException("Ambiente já está ocupado.");
        }

        return super.create(dto);
    }

    // editar reserva apenas se não iniciada
    @Override
    public ReservaDTO update(Long id, ReservaDTO dto) {
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        
        // bloqueia se a reserva for de um dia anterior a hoje
        if (!reserva.getDataInicio().toLocalDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Não é possível editar reservas de hoje ou anteriores!");
        }

        // se passou na verificação, segue com a atualização normal
        return super.update(id, dto);
    }
}
