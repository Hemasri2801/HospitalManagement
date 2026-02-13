package com.example.hospitalmanagement.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

/**
 * DTO returned to clients for doctor details.
 */
public class DoctorResponseDTO {
    private Long id;
    private String name;
    private String specialization;
    private String email;
    private String phone;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime availableFrom;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime availableTo;
    private String message;

    public DoctorResponseDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
