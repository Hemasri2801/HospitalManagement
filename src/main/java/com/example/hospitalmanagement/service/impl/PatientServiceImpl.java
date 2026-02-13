package com.example.hospitalmanagement.service.impl;

import com.example.hospitalmanagement.dto.PatientRequestDTO;
import com.example.hospitalmanagement.dto.PatientResponseDTO;
import com.example.hospitalmanagement.entity.Patient;
import com.example.hospitalmanagement.exception.ApiException;
import com.example.hospitalmanagement.repository.AppointmentRepository;
import com.example.hospitalmanagement.repository.PatientRepository;
import com.example.hospitalmanagement.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;


/**
 * Service implementation for managing patients including CRUD operations
 * with validation to prevent deletion when linked appointments exist.
 */

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;


    public PatientServiceImpl(PatientRepository patientRepository,AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;

    }

    @Override
    public PatientResponseDTO addPatient(PatientRequestDTO dto) {

        log.info("Creating patient with email:{}", dto.getEmail());
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        patient.setPhone(dto.getPhone());
        patient.setDateOfBirth(dto.getDateOfBirth());

        Patient savedPatient = patientRepository.save(patient);
        log.info("Patient created successfully with Id: {}", savedPatient.getId());

        return mapToResponseDTO(savedPatient, "Patient created successfully");
    }

    @Override
    public List<PatientResponseDTO> getAllPatients() {
        log.info("Fetching all patients");
        return patientRepository.findAll().stream()
                .map(patient -> mapToResponseDTO(patient, null))
                .collect(Collectors.toList());
    }

    @Override
    public PatientResponseDTO getPatientById(Long id) {
        log.info("Fetching patient with Id: {}", id);
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Patient not found with Id:{}", id);
                    return new ApiException("Patient not found with Id:" + id,
                            HttpStatus.NOT_FOUND);
                });

        return mapToResponseDTO(patient, null);
    }

    @Override
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO dto) {
        log.info("Updating patient with Id:{}", id);
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Patient not found with ID:{}", id);
                    return new ApiException("Patient not found with Id:" + id,
                            HttpStatus.NOT_FOUND);
                });
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        patient.setPhone(dto.getPhone());
        patient.setDateOfBirth(dto.getDateOfBirth());

        Patient updatedPatient = patientRepository.save(patient);
        log.info("Patient updated successfully with Id: {}", id);
        return mapToResponseDTO(updatedPatient, "Patient updated successfully");
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Patient not found with the Id: {}", id);
                    return new ApiException("Patient not found with Id:" + id,
                            HttpStatus.NOT_FOUND);
                });
        if (appointmentRepository.existsByPatientId(id)) {
            throw new ApiException(
                    "Cannot delete patient with existing appointments",
                    HttpStatus.CONFLICT);
        }

        patientRepository.delete(patient);
    }

    private PatientResponseDTO mapToResponseDTO(Patient patient, String message) {
        PatientResponseDTO dto = new PatientResponseDTO();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setEmail(patient.getEmail());
        dto.setPhone(patient.getPhone());
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setMessage(message);
        return dto;
    }
}
