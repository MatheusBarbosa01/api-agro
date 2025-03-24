package com.application.agriculture.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.agriculture.dto.DadosAtualizarUsuario;
import com.application.agriculture.dto.DadosCadastrarUsuario;
import com.application.agriculture.dto.DadosDetalhamentoUsuario;
import com.application.agriculture.dto.DadosFiltroUsuario;
import com.application.agriculture.model.Usuario;
import com.application.agriculture.model.UsuarioSpecification;
import com.application.agriculture.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.application.agriculture.dto.DadosListagemUsuario;


@Service
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public DadosDetalhamentoUsuario cadastrarUsuario(DadosCadastrarUsuario dados) {
        if (usuarioRepository.findByEmail(dados.email()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        String senhaCriptografada = passwordEncoder.encode(dados.senha());
    
        Usuario usuario = new Usuario(null, dados.email(), dados.nome(), senhaCriptografada);
        usuarioRepository.save(usuario);

        return new DadosDetalhamentoUsuario(usuario);
    }

    public Page<DadosListagemUsuario> buscarPorFiltros(DadosFiltroUsuario filtro, Pageable paginacao) {
        Specification<Usuario> spec = UsuarioSpecification.filtrar(filtro);
        return usuarioRepository.findAll(spec, paginacao).map(DadosListagemUsuario::new);
    }

    public Page<DadosListagemUsuario> listarUsuario(@PageableDefault(size = 10) Pageable paginacao) {
        Page<Usuario> listarUsuario = usuarioRepository.findAll(paginacao);
        return listarUsuario.map(DadosListagemUsuario::new);
    }

    @Transactional
    public DadosDetalhamentoUsuario atualizarUsuario(Long id, DadosAtualizarUsuario dados) {
        Usuario usuarioAtualizado = usuarioRepository.getReferenceById(id);
        String senhaCodificada = null;
        String descricao = "Edição de dados do usuário: " + usuarioAtualizado.getNome();

        if (dados.senha() != null) {
            senhaCodificada = passwordEncoder.encode(dados.senha());
            descricao += ", mudança de senha";
        }
        if (dados.nome() != null && !usuarioAtualizado.getNome().equals(dados.nome())) {
            descricao += ", mudança do nome de " + usuarioAtualizado.getNome() + " para " + dados.nome();
        }
        if (dados.email() != null && !usuarioAtualizado.getEmail().equals(dados.email())) {
            descricao += ", mudança do email de " + usuarioAtualizado.getEmail() + " para " + dados.email();
        }

        usuarioAtualizado.atualizarUsuario(dados, senhaCodificada);
        
        usuarioRepository.save(usuarioAtualizado);
        log.info(descricao);

        return new DadosDetalhamentoUsuario(usuarioAtualizado);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Long getUsuarioLogadoId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
        if (principal instanceof Usuario usuario) {
            return usuario.getId(); 
        }
    
        if (principal instanceof UserDetails userDetails) {
            return usuarioRepository.findByEmail(userDetails.getUsername())
                    .map(Usuario::getId)
                    .orElse(null);
        }
    
        return null;
    }    
}
