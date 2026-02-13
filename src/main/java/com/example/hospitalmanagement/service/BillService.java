package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.dto.BillRequestDTO;
import com.example.hospitalmanagement.dto.BillResponseDTO;

import java.util.List;
/**
 * Service interface for managing billing operations.
 */

public interface BillService {
    BillResponseDTO generateBill(BillRequestDTO dto);

    BillResponseDTO getBillById(Long id);

    List<BillResponseDTO> getBillsByPatientId(Long patientId);

}
