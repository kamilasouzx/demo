package com.example.demo.DTO;

import java.time.LocalDateTime;

import com.example.demo.entity.Ambiente;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @NotBlank(message = "O motivo deve ser preenchido")
    private String motivo;

    @NotBlank(message = "O nome deve ser preenchido")
    private String nome;

    @NotNull(message = "A data de início deve ser preenchida")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataInicio;

    @NotNull(message = "A hora de início deve ser preenchida")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataFim;

    @NotNull(message = "O ambiente deve ser preenchido")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Ambiente ambiente;
}
