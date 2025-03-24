package com.application.agriculture.dto;

import com.application.agriculture.model.*;

public record DadosDetalhamentoUsuario(
    Long id,
    String nome,
    String email
) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail()
        );
    }
}
