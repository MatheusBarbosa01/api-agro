package com.application.agriculture.repository;

import com.application.agriculture.model.Planting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantingRepository extends JpaRepository<Planting, Long> {
    List<Planting> findByUserId(Long userId);  
}
