package com.application.agriculture.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosCadastrarUsuario(
    @NotBlank
    @Size(max = 100)
    String nome,

    @NotBlank
    @Email
    @Size(max = 100)
    String email,
    
    @NotBlank
    @Size(min = 6, max = 255)
    String senha
) {
}
