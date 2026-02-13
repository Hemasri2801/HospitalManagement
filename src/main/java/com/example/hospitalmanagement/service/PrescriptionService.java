package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.dto.PrescriptionRequestDTO;
import com.example.hospitalmanagement.dto.PrescriptionResponseDTO;

/**
 * Service interface for managing prescription-related operations.
 */

public interface PrescriptionService {
    PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO dto);

    PrescriptionResponseDTO getPrescriptionByAppointmentId(Long appointmentId);

}
