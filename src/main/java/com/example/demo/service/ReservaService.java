package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public List<ReservaDTO> listaPorData(String dataInicio, String dataFim){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime inicio = LocalDate.parse(dataInicio, formatter).atStartOfDay();
        LocalDateTime fim = LocalDate.parse(dataFim, formatter).atTime(23, 59, 59);

        List<Reserva> reservas = repository.findByDatas(inicio, fim);
        List<ReservaDTO> dtos = new ArrayList<>();

        for (Reserva reserva : reservas) {
            dtos.add(super.toDto(reserva));
        }
        return dtos;
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

    public List<ReservaDTO> listaPorAmbiente(Long id) {
        List<Reserva> reservas = repository.findByAmbiente(id);
        List<ReservaDTO> dtos = new ArrayList<>(); 

        for (Reserva reserva : reservas) {
            dtos.add(super.toDto(reserva));
        }
        return dtos;
    }

    //listar por nome
    public List<ReservaDTO> listaPorNome(String nome) {
        List<Reserva> reservas = repository.findByNome(nome);
        List<ReservaDTO> dtos = new ArrayList<>();

        for (Reserva reserva : reservas) {
            dtos.add(super.toDto(reserva));
        }
        return dtos;
    }
}
