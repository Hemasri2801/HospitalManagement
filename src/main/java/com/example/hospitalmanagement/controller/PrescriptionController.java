package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.dto.PrescriptionRequestDTO;
import com.example.hospitalmanagement.dto.PrescriptionResponseDTO;
import com.example.hospitalmanagement.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    //@PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/Prescription")
    public ResponseEntity<PrescriptionResponseDTO> createPrescription(@Valid @RequestBody PrescriptionRequestDTO dto) {
        return ResponseEntity.ok(prescriptionService.createPrescription(dto));
    }

    //@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    @GetMapping("Prescription/appointment/{appointmentId}")
    public ResponseEntity<PrescriptionResponseDTO> getByAppointment(
            @PathVariable Long appointmentId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionByAppointmentId(appointmentId));

    }

}
