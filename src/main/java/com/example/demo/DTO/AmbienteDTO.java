package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmbienteDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "O nome deve ser preenchido")
    private String descricao;

    @NotBlank(message = "A localização deve ser preenchida")
    private String localizacao;

    @NotNull(message = "A capacidade deve ser preenchida")
    private int capacidade;
    
}
