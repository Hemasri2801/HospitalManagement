package com.example.hospitalmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

/**
 * DTO for creating and updating doctors.
 */

public class DoctorRequestDTO {

    @NotBlank(message = "Doctor name must not be blank")
    private String name;
    @NotBlank(message = "Specialization must not be blank")
    private String specialization;
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;
    @NotBlank(message = "Phone number must not be blank")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 to 15 digits")
    private String phone;
    @NotNull(message = "Available from time must not be null")
    @Schema(type = "string", example = "09:00")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime availableFrom;
    @NotNull(message = "Available to time must not ne null")
    @Schema(type = "String", example = "17:00")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime availableTo;

    public DoctorRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalTime getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    public LocalTime getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(LocalTime availableTo) {
        this.availableTo = availableTo;
    }
}
