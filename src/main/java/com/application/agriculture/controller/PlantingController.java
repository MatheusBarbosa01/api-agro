package com.application.agriculture.controller;

import com.application.agriculture.dto.PlantingDTO;
import com.application.agriculture.model.Planting;
import com.application.agriculture.services.PlantingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plantings")
@RequiredArgsConstructor
public class PlantingController {

    private final PlantingService plantingService;

    @PostMapping
    public ResponseEntity<Planting> create(@RequestBody PlantingDTO dto) {
        return ResponseEntity.ok(plantingService.registerPlanting(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Planting>> history(@PathVariable Long userId) {
        return ResponseEntity.ok(plantingService.getPlantingHistory(userId));
    }
}
