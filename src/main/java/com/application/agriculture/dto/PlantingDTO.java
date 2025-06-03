package com.application.agriculture.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlantingDTO {
    private String cropType;
    private String seedType;
    private String soilType;
    private String city;
    private Double landSize;
    private LocalDate date;
    private Long userId;
}
