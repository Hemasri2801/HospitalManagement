package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.dto.PatientRequestDTO;
import com.example.hospitalmanagement.dto.PatientResponseDTO;
import com.example.hospitalmanagement.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PatientController {

    private static final Logger log = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO requestDTO) {
        log.info("Received request to create patient");
        PatientResponseDTO responseDTO = patientService.addPatient(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    @GetMapping("/Patient/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        log.info("Received request to fetch patient with id{}", id);
        PatientResponseDTO responseDTO = patientService.getPatientById(id);
        return ResponseEntity.ok(responseDTO);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Patients")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        log.info("Received request to fetch all patients");
        List<PatientResponseDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/Patient/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO requestDTO) {
        log.info("Received request to update patient with id{}", id);
        PatientResponseDTO responseDTO = patientService.updatePatient(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/Patient/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        log.info("Received request to delete patient with id{}", id);
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
