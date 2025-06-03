package com.application.agriculture.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import com.application.agriculture.model.Usuario;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                .withIssuer("API agro.agriculture")
                .withSubject(usuario.getUsername())
                .withClaim("nome", usuario.getNome())
                .withExpiresAt(dataExpiracao())
                .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }	
    }

    public String getSubject(String tokenJWT) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
            .withIssuer("API agro.agriculture")
            .build()
            .verify(tokenJWT)
            .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido ou expirado.");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
    
}