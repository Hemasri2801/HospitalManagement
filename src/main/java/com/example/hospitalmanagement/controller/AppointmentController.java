package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.dto.AppointmentRequestDTO;
import com.example.hospitalmanagement.dto.AppointmentResponseDTO;
import com.example.hospitalmanagement.service.AppointmentService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import java.util.List;

/**
 * REST Controller for managing appointments.
 */

@RestController
@RequestMapping("/api/v1")
public class AppointmentController {

    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    //@PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/Appointment")
    public ResponseEntity<AppointmentResponseDTO> createAppointment(
            @Valid @RequestBody AppointmentRequestDTO requestDTO) {
        log.info("Received request to create appointment");

        AppointmentResponseDTO response = appointmentService.createAppointment(requestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    //@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    @GetMapping("/Appointment/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(@PathVariable Long id) {
        AppointmentResponseDTO response = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments() {
        List<AppointmentResponseDTO> response = appointmentService.getAllAppointments();
        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    @GetMapping("/Appointment/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByPatientId(@PathVariable Long patientId) {
        log.info("Received request to fetch appointments for patientId:{}", patientId);
        List<AppointmentResponseDTO> response = appointmentService.getAppointmentsByPatientId(patientId);
        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    @DeleteMapping("Appointment/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok("Appointment cancelled successfully");
    }
}
