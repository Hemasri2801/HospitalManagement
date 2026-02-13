package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.dto.DoctorRequestDTO;
import com.example.hospitalmanagement.dto.DoctorResponseDTO;

import java.util.List;

/**
 * Service interface for Doctor operations.
 */

public interface DoctorService {

    DoctorResponseDTO addDoctor(DoctorRequestDTO requestDTO);

    DoctorResponseDTO getDoctorById(Long id);

    List<DoctorResponseDTO> getAllDoctors();

    DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO requestDTO);

    void deleteDoctor(Long id);
}
