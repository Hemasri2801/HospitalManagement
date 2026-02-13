package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Prescription entities.
 */

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Optional<Prescription> findByAppointmentId(Long appointmentId);
    boolean existsByAppointmentId(Long appointmentId);

}
