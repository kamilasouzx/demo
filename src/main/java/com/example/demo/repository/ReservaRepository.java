package com.example.demo.repository;
 
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Reserva;

@Repository
public interface ReservaRepository extends BaseRepository<Reserva, Long> {
    
    @Query("""
        SELECT COUNT(r) <= 0
        FROM Reserva r 
        WHERE r.ambiente.id = :id 
        AND r.ativo = true
        AND (
            (r.dataInicio < :dataFim AND r.dataFim > :dataInicio)
        )
    """)
    boolean ambienteDisponivel(
        @Param("id") Long id,
        @Param("dataInicio") LocalDateTime dataInicio,
        @Param("dataFim") LocalDateTime dataFim);


        @Query("""
        SELECT r
        FROM Reserva r
        WHERE r.ativo = true
        AND (
        r.dataInicio BETWEEN :dataInicio AND :dataFim OR
        r.dataFim BETWEEN :dataInicio AND :dataFim)
        """)

        List<Reserva> findByDatas(LocalDateTime dataInicio, LocalDateTime dataFim);

        @Query("""
        SELECT r
        FROM Reserva r
        WHERE r.ativo = true
        AND r.ambiente.id = :ambienteId
        """)
        List<Reserva> findByAmbiente(@Param("ambienteId") Long ambienteId);
}