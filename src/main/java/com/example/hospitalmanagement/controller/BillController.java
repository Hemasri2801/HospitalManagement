package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.dto.BillRequestDTO;
import com.example.hospitalmanagement.dto.BillResponseDTO;
import com.example.hospitalmanagement.service.BillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for managing bills.
 */
@RestController
@RequestMapping("/api/v1")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Bill")
    public ResponseEntity<BillResponseDTO> generateBill(@Valid @RequestBody BillRequestDTO requestDTO) {
        BillResponseDTO response = billService.generateBill(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    @GetMapping("/Bill/{id}")
    public ResponseEntity<BillResponseDTO> getBillById(@PathVariable Long id) {
        BillResponseDTO response = billService.getBillById(id);
        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    @GetMapping("Bill/patient/{patientId}")
    public ResponseEntity<List<BillResponseDTO>> getBillsByPatientId(
            @PathVariable Long patientId) {
        List<BillResponseDTO> response = billService.getBillsByPatientId(patientId);
        return ResponseEntity.ok(response);

    }

}
