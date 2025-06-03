package com.application.agriculture.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "plantings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Planting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cropType;     
    private String seedType;     
    private String soilType;     
    private String city;         
    private Double landSize;     
    private LocalDate date;      

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;           
}
