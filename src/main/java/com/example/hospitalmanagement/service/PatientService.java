package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.dto.PatientRequestDTO;
import com.example.hospitalmanagement.dto.PatientResponseDTO;

import java.util.List;

/**
 * Service interface for Patient operations.
 */
public interface PatientService {
    PatientResponseDTO addPatient(PatientRequestDTO requestDTO);

    PatientResponseDTO getPatientById(Long id);

    List<PatientResponseDTO> getAllPatients();

    PatientResponseDTO updatePatient(Long id, PatientRequestDTO requestDTO);

    void deletePatient(Long id);

}
