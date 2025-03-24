package com.application.agriculture.dto;

import jakarta.validation.constraints.Email;

public record DadosAtualizarUsuario(
    String nome,
    
    @Email
    String email,
    
    String senha
    
) {
}
