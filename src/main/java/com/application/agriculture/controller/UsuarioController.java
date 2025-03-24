package com.application.agriculture.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.application.agriculture.dto.DadosAtualizarUsuario;
import com.application.agriculture.dto.DadosCadastrarUsuario;
import com.application.agriculture.dto.DadosDetalhamentoUsuario;
import com.application.agriculture.dto.DadosFiltroUsuario;
import com.application.agriculture.dto.DadosListagemUsuario;
import com.application.agriculture.model.Usuario;
import com.application.agriculture.services.UsuarioService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/me")
    public Usuario getCurrentUser(@AuthenticationPrincipal Usuario usuario) {
        return usuario;
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemUsuario>> listarUsuario(Pageable paginacao) {
        Page<DadosListagemUsuario> usuarios = usuarioService.listarUsuario(paginacao);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<DadosListagemUsuario>> buscarPorFiltros(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @PageableDefault(sort = "nome", direction = Sort.Direction.ASC) Pageable paginacao) {
        DadosFiltroUsuario filtro = new DadosFiltroUsuario(nome, email);
        return ResponseEntity.ok(usuarioService.buscarPorFiltros(filtro, paginacao));
    }
    
    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrar(@RequestBody @Valid DadosCadastrarUsuario dados, UriComponentsBuilder uriBuilder) {
        DadosDetalhamentoUsuario usuarioDetalhado = usuarioService.cadastrarUsuario(dados);
        var uri = uriBuilder.path("/api/usuarios/{id}").buildAndExpand(usuarioDetalhado.id()).toUri();
        return ResponseEntity.created(uri).body(usuarioDetalhado);
    }
    
    @PutMapping("/atualizar/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoUsuario> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid DadosAtualizarUsuario dados) {
        DadosDetalhamentoUsuario usuarioAtualizado = usuarioService.atualizarUsuario(id, dados);
        return ResponseEntity.ok(usuarioAtualizado);
    }
    
    @DeleteMapping("/deletar/{id}")
    @Transactional
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
