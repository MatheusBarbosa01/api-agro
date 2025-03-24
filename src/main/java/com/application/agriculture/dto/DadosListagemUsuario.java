package com.application.agriculture.dto;

import com.application.agriculture.model.*;

public record DadosListagemUsuario(
    Long id,
    String nome,
    String email
) {
    public DadosListagemUsuario(Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail()
        );
    }
}
