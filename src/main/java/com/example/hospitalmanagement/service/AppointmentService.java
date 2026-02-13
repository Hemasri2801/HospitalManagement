package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.dto.AppointmentRequestDTO;
import com.example.hospitalmanagement.dto.AppointmentResponseDTO;

import java.util.List;

/**
 * Service interface for Appointment operations.
 */
public interface AppointmentService {
    AppointmentResponseDTO createAppointment(AppointmentRequestDTO requestDTO);

    AppointmentResponseDTO getAppointmentById(Long id);

    List<AppointmentResponseDTO> getAllAppointments();

    List<AppointmentResponseDTO> getAppointmentsByPatientId(Long patientId);

    void cancelAppointment(Long id);
}
