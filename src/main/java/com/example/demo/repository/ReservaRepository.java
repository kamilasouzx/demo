package com.example.demo.repository;
 
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Reserva;

@Repository
public interface ReservaRepository extends BaseRepository<Reserva, Long> {
    
}
