package com.example.hospitalmanagement.service.impl;

import com.example.hospitalmanagement.dto.BillRequestDTO;
import com.example.hospitalmanagement.dto.BillResponseDTO;
import com.example.hospitalmanagement.entity.Appointment;
import com.example.hospitalmanagement.entity.Bill;
import com.example.hospitalmanagement.exception.ApiException;
import com.example.hospitalmanagement.repository.AppointmentRepository;
import com.example.hospitalmanagement.repository.BillRepository;
import com.example.hospitalmanagement.service.BillService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Service implementation for managing bill operations such as generation and retrieval.
 */

@Service
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final AppointmentRepository appointmentRepository;

    public BillServiceImpl(BillRepository billRepository, AppointmentRepository appointmentRepository) {
        this.billRepository = billRepository;
        this.appointmentRepository = appointmentRepository;

    }

    @Override
    public BillResponseDTO generateBill(BillRequestDTO dto) {
        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new ApiException("Appointment not found with id:" + dto.getAppointmentId(),
                        HttpStatus.NOT_FOUND));

        billRepository.findByAppointmentId(dto.getAppointmentId())
                .ifPresent(b -> {
                    throw new ApiException("Bill already generated for this appointment",
                            HttpStatus.CONFLICT);
                });

        Bill bill = new Bill();
        bill.setAppointment(appointment);
        bill.setPatient(appointment.getPatient());
        bill.setConsultationFee(dto.getConsultationFee());
        bill.setMedicineCharges(dto.getMedicineCharges());

        double total = dto.getConsultationFee()
                + dto.getMedicineCharges();
        bill.setTotalAmount(total);
        bill.setPaymentStatus("PENDING");
        bill.setBillDate(LocalDate.now());

        Bill saved = billRepository.save(bill);
        return mapToDTO(saved);
    }

    @Override
    public BillResponseDTO getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ApiException("Bill not found with id:" + id,
                        HttpStatus.NOT_FOUND));
        return mapToDTO(bill);
    }

    @Override
    public List<BillResponseDTO> getBillsByPatientId(Long patientId) {
        List<Bill> bills = billRepository.findByAppointmentPatientId(patientId);
        if (bills.isEmpty()) {
            throw new ApiException(
                    "No bills found for patient id:" + patientId,
                    HttpStatus.NOT_FOUND);
        }
        return bills.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private BillResponseDTO mapToDTO(Bill bill) {
        BillResponseDTO dto = new BillResponseDTO();
        dto.setId(bill.getId());
        dto.setAppointmentId(bill.getAppointment().getId());
        dto.setConsultationFee(bill.getConsultationFee());
        dto.setPatientId(bill.getPatient().getId());
        dto.setMedicineCharges(bill.getMedicineCharges());
        dto.setTotalAmount(bill.getTotalAmount());
        dto.setPaymentStatus(bill.getPaymentStatus());
        dto.setBillDate(bill.getBillDate());
        return dto;
    }
}
