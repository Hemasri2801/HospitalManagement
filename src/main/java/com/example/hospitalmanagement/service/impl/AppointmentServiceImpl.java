package com.example.hospitalmanagement.service.impl;

import com.example.hospitalmanagement.dto.AppointmentRequestDTO;
import com.example.hospitalmanagement.dto.AppointmentResponseDTO;
import com.example.hospitalmanagement.entity.Appointment;
import com.example.hospitalmanagement.entity.Doctor;
import com.example.hospitalmanagement.entity.Patient;
import com.example.hospitalmanagement.exception.ApiException;
import com.example.hospitalmanagement.repository.AppointmentRepository;
import com.example.hospitalmanagement.repository.PatientRepository;
import com.example.hospitalmanagement.repository.DoctorRepository;
import com.example.hospitalmanagement.repository.PrescriptionRepository;
import com.example.hospitalmanagement.repository.BillRepository;
import com.example.hospitalmanagement.service.AppointmentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing appointments including booking, retrieval,
 * and safe cancellation with validation against existing prescriptions and bills.
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger log =
            LoggerFactory.getLogger(AppointmentServiceImpl.class);

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final BillRepository billRepository;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            PrescriptionRepository prescriptionRepository,
            BillRepository billRepository) {

        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.billRepository = billRepository;
    }

    @Override
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {

        log.info("Creating appointment for patientId={} and doctorId={}",
                dto.getPatientId(), dto.getDoctorId());

        boolean alreadyBooked =
                appointmentRepository
                        .existsByDoctorIdAndAppointmentDateAndAppointmentTime(
                                dto.getDoctorId(),
                                dto.getAppointmentDate(),
                                dto.getAppointmentTime()
                        );

        if (alreadyBooked) {
            throw new ApiException(
                    "Doctor is already booked at this date and time",
                    HttpStatus.CONFLICT);
        }

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ApiException(
                        "Patient not found with id: " + dto.getPatientId(),
                        HttpStatus.NOT_FOUND));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ApiException(
                        "Doctor not found with id: " + dto.getDoctorId(),
                        HttpStatus.NOT_FOUND));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setStatus("BOOKED");

        Appointment saved = appointmentRepository.save(appointment);

        log.info("Appointment created successfully with id {}", saved.getId());

        return mapToResponseDTO(saved);
    }

    @Override
    public AppointmentResponseDTO getAppointmentById(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException("Appointment not found with id: " + id,
                                HttpStatus.NOT_FOUND));

        return mapToResponseDTO(appointment);
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointments() {

        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByPatientId(Long patientId) {

        log.info("Fetching appointments for patientId {}", patientId);

        patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ApiException("Patient not found with id: " + patientId,
                                HttpStatus.NOT_FOUND));

        List<Appointment> appointments =
                appointmentRepository.findByPatientId(patientId);

        if (appointments.isEmpty()) {
            throw new ApiException(
                    "No appointments found for patient with id: " + patientId,
                    HttpStatus.NOT_FOUND);
        }

        return appointments.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException("Appointment not found with id: " + id,
                                HttpStatus.NOT_FOUND));

        if (prescriptionRepository.existsByAppointmentId(id)) {
            throw new ApiException(
                    "Cannot delete appointment with existing prescription",
                    HttpStatus.CONFLICT);
        }

        if (billRepository.existsByAppointmentId(id)) {
            throw new ApiException(
                    "Cannot delete appointment with existing bill",
                    HttpStatus.CONFLICT);
        }

        appointmentRepository.delete(appointment);

        log.info("Appointment deleted successfully with id {}", id);
    }

    private AppointmentResponseDTO mapToResponseDTO(Appointment appointment) {

        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appointment.getId());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setStatus(appointment.getStatus());

        return dto;
    }
}
