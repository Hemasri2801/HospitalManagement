package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.dto.DoctorRequestDTO;
import com.example.hospitalmanagement.dto.DoctorResponseDTO;
import com.example.hospitalmanagement.service.DoctorService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import java.util.List;


/**
 * REST controller for Doctor-related APIs.
 */
@RestController
@RequestMapping("/api/v1")
public class DoctorController {
    private static final Logger log = LoggerFactory.getLogger(DoctorController.class);
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Doctor")
    public ResponseEntity<DoctorResponseDTO> createDoctor(
            @Valid @RequestBody DoctorRequestDTO requestDTO) {
        log.info("Received request to create doctor");
        DoctorResponseDTO responseDTO = doctorService.addDoctor(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    @GetMapping("Doctor/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable Long id) {
        log.info("Received request to get doctor with id:{}", id);
        DoctorResponseDTO responseDTO = doctorService.getDoctorById(id);
        return ResponseEntity.ok(responseDTO);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    @GetMapping("/Doctors")
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {
        log.info("Received request to get all doctors");
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/Doctor/{id}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(
            @PathVariable Long id, @Valid @RequestBody DoctorRequestDTO requestDTO) {
        log.info("Received request to update doctor with id:{}", id);
        DoctorResponseDTO responseDTO = doctorService.updateDoctor(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/Doctor/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        log.info("Received request to delete doctor with id: {}", id);
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

}
