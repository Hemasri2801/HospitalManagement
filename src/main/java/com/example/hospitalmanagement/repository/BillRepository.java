package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Bill entity.
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByAppointmentId(Long appointmentId);

    List<Bill> findByAppointmentPatientId(Long patientId);
    boolean existsByAppointmentId(Long appointmentId);

}
