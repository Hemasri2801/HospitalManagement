package com.example.hospitalmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


/**
 * Global exception handler for the Hospital Management application.
 * This class centralizes exception handling across all REST controllers.
 * It converts exceptions into meaningful HTTP responses so that
 * clients receive consistent and user-friendly error messages.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getStatus().value(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(
                400, "Validation failed", errors
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }


//    @ExceptionHandler(PatientNotFoundException.class)
//    public ResponseEntity<Map<String,Object>> handlePatientNotFound(
//            PatientNotFoundException ex){
//        Map<String,Object> response=new HashMap<>();
//        response.put("timestamp", LocalDateTime.now());
//        response.put("status", HttpStatus.NOT_FOUND.value());
//        response.put("error",ex.getMessage());
//
//        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String,Object>> handleValidationErrors(
//            org.springframework.web.bind.MethodArgumentNotValidException ex){
//        Map<String,Object> response=new HashMap<>();
//        response.put("timestamp",LocalDate.now());
//        response.put("status",HttpStatus.BAD_REQUEST.value());
//
//        List<String> errors=ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(error->error.getDefaultMessage())
//                .toList();
//        response.put("errors",errors);
//        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<Map<String,Object>> handleResourceNotFound(
//            ResourceNotFoundException ex){
//        Map<String,Object> response=new HashMap<>();
//        response.put("timestamp",LocalDateTime.now());
//        response.put("status",HttpStatus.NOT_FOUND.value());
//        response.put("error",ex.getMessage());
//
//        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(BookingConflictException.class)
//    public ResponseEntity<Map<String,Object>> handleBookingConflict(
//            BookingConflictException ex) {
//        Map<String,Object> response=new HashMap<>();
//        response.put("timestamp",LocalDateTime.now());
//        response.put("status",HttpStatus.CONFLICT.value());
//        response.put("error",ex.getMessage());
//
//        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
//
//    }


}
