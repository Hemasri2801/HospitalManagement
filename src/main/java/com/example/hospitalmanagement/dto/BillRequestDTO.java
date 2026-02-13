package com.example.hospitalmanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO for creating a bill.
 */
public class BillRequestDTO {
    @NotNull(message = "Appointment ID must not be null")
    private Long appointmentId;
    @NotNull(message = "Consultation fee must not be null")
    @Positive(message = "Consultation fee must be greater than 0")
    private Double consultationFee;
    @NotNull(message = "Medicine charges must not be null")
    @Positive(message = "Medicine charges must be greater than 0")
    private Double medicineCharges;

    public BillRequestDTO() {
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(Double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public Double getMedicineCharges() {
        return medicineCharges;
    }

    public void setMedicineCharges(Double medicineCharges) {
        this.medicineCharges = medicineCharges;
    }
}
