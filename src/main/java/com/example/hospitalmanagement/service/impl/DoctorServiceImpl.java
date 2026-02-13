package com.example.hospitalmanagement.service.impl;

import com.example.hospitalmanagement.dto.DoctorRequestDTO;
import com.example.hospitalmanagement.dto.DoctorResponseDTO;
import com.example.hospitalmanagement.entity.Doctor;
import com.example.hospitalmanagement.exception.ApiException;
import com.example.hospitalmanagement.repository.AppointmentRepository;
import com.example.hospitalmanagement.repository.DoctorRepository;
import com.example.hospitalmanagement.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing doctors including CRUD operations
 * with validation to prevent deletion when appointments exist.
 */


@Service
public class DoctorServiceImpl implements DoctorService {
    private static final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository,AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public DoctorResponseDTO addDoctor(DoctorRequestDTO dto) {
        log.info("Creating doctor with email:{}", dto.getEmail());

        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setEmail(dto.getEmail());
        doctor.setPhone(dto.getPhone());
        doctor.setAvailableForm(dto.getAvailableFrom());
        doctor.setAvailableTo(dto.getAvailableTo());

        Doctor savedDoctor = doctorRepository.save(doctor);
        log.info("Doctor created with id:{}", savedDoctor.getId());
        return mapToResponseDTO(savedDoctor, "Doctor created successfully");
    }

    @Override
    public DoctorResponseDTO getDoctorById(Long id) {
        log.info("Fetching doctor with id:{}", id);
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> {
            log.error("Doctor not found with id:{}", id);
            return new ApiException("Doctor not found with id:" + id,
                    HttpStatus.NOT_FOUND);
        });
        return mapToResponseDTO(doctor, null);
    }

    @Override
    public List<DoctorResponseDTO> getAllDoctors() {
        log.info("Fetching all doctors");
        return doctorRepository.findAll().stream()
                .map(doctor -> mapToResponseDTO(doctor, null))
                .collect(Collectors.toList());
    }

    @Override
    public DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO dto) {
        log.info("Updating doctor with id:{}", id);
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Doctor not found with ID:{}", id);
                    return new ApiException("Doctor not found with id:" + id,
                            HttpStatus.NOT_FOUND);
                });
        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setEmail(dto.getEmail());
        doctor.setPhone(dto.getPhone());
        doctor.setAvailableForm(dto.getAvailableFrom());
        doctor.setAvailableTo(dto.getAvailableTo());

        Doctor updatedDoctor = doctorRepository.save(doctor);
        log.info("Doctor updated with id:{}", id);
        return mapToResponseDTO(updatedDoctor, "Doctor updated successfully");
    }

    @Override
    public void deleteDoctor(Long id) {
        log.info("Deleting doctor with id:{}", id);

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Doctor not found with the id:{}", id);
                    return new ApiException("Doctor not found with id:" + id,
                            HttpStatus.NOT_FOUND);
                });

        if (appointmentRepository.existsByDoctorId(id)) {
            throw new ApiException(
                    "Cannot delete doctor with existing appointments",
                    HttpStatus.CONFLICT);
        }

        doctorRepository.delete(doctor);
    }

    private DoctorResponseDTO mapToResponseDTO(Doctor doctor, String message) {
        DoctorResponseDTO dto = new DoctorResponseDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setEmail(doctor.getEmail());
        dto.setPhone(doctor.getPhone());
        dto.setAvailableFrom(doctor.getAvailableForm());
        dto.setAvailableTo(doctor.getAvailableTo());
        dto.setMessage(message);
        return dto;
    }
}
