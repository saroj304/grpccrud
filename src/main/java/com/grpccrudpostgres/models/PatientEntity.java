package com.grpccrudpostgres.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String doctor_name;
    private String address;
    private List<String>patientsymptoms;
}
