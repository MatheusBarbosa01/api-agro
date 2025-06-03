package com.application.agriculture.services;

import com.application.agriculture.dto.PlantingDTO;
import com.application.agriculture.model.Planting;
import com.application.agriculture.model.Usuario;
import com.application.agriculture.repository.PlantingRepository;
import com.application.agriculture.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantingService {

    private final PlantingRepository plantingRepository;
    private final UsuarioRepository usuarioRepository;

    public Planting registerPlanting(PlantingDTO dto) {
        Usuario user = usuarioRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Planting planting = Planting.builder()
                .cropType(dto.getCropType())
                .seedType(dto.getSeedType())
                .soilType(dto.getSoilType())
                .city(dto.getCity())
                .landSize(dto.getLandSize())
                .date(dto.getDate())
                .user(user)
                .build();

        return plantingRepository.save(planting);
    }

    public List<Planting> getPlantingHistory(Long userId) {
        return plantingRepository.findByUserId(userId);
    }
}
