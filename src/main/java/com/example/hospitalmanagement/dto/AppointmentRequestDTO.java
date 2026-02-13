package com.example.hospitalmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for creating and updating appointments.
 */
public class AppointmentRequestDTO {

    @NotNull(message = "Patient ID must not be null")
    private Long patientId;
    @NotNull(message = "Doctor ID must not be null")
    private Long doctorId;
    @NotNull(message = "Appointment date must not be null")
    private LocalDate appointmentDate;
    @Schema(type = "string", example = "10:30:00")
    @JsonFormat(pattern = "HH:mm:ss")
    @NotNull(message = "Appointment time must not be null")
    private LocalTime appointmentTime;

    public AppointmentRequestDTO() {
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }


}
