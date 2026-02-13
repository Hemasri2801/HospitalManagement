package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Repository interface for Appointment entity.
 * Provides CRUD operations using Spring Data JPA.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorIdAndAppointmentDateAndAppointmentTime(
            Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime
    );

    List<Appointment> findByPatientId(Long patientId);
    boolean existsByPatientId(Long patientId);
    boolean existsByDoctorId(Long doctorId);


}
