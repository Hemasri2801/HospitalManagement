package com.example.hospitalmanagement.service.impl;

import com.example.hospitalmanagement.dto.PrescriptionRequestDTO;
import com.example.hospitalmanagement.dto.PrescriptionResponseDTO;
import com.example.hospitalmanagement.entity.Appointment;
import com.example.hospitalmanagement.entity.Prescription;
import com.example.hospitalmanagement.exception.ApiException;
import com.example.hospitalmanagement.repository.AppointmentRepository;
import com.example.hospitalmanagement.repository.PrescriptionRepository;
import com.example.hospitalmanagement.service.PrescriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
/**
 * Service implementation for handling prescription business logic.
 */

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, AppointmentRepository appointmentRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO dto) {
        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new ApiException("Appointment not found with id:" + dto.getAppointmentId(),
                        HttpStatus.NOT_FOUND));

        prescriptionRepository.findByAppointmentId(dto.getAppointmentId())
                .ifPresent(p -> {
                    throw new ApiException("Prescription already exist for the appointment",
                            HttpStatus.CONFLICT);
                });
        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment);
        prescription.setDiagnosis(dto.getDiagnosis());
        prescription.setMedicines(dto.getMedicines());
        prescription.setNotes(dto.getNotes());
        prescription.setPrescribedDate(LocalDate.now());

        Prescription saved = prescriptionRepository.save(prescription);
        return mapToDTO(saved);
    }

    @Override
    public PrescriptionResponseDTO getPrescriptionByAppointmentId(Long appointmentId) {
        Prescription prescription = prescriptionRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new ApiException("Prescription not found for appointment id:" + appointmentId,
                        HttpStatus.NOT_FOUND));
        return mapToDTO(prescription);
    }

    private PrescriptionResponseDTO mapToDTO(Prescription prescription) {
        PrescriptionResponseDTO dto = new PrescriptionResponseDTO();
        dto.setId(prescription.getId());
        dto.setAppointmentId(prescription.getAppointment().getId());
        dto.setDiagnosis(prescription.getDiagnosis());
        dto.setMedicines(prescription.getMedicines());
        dto.setNotes(prescription.getNotes());
        dto.setPrescribedDate(prescription.getPrescribedDate());

        return dto;
    }

}
