package com.zyra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.zyra.dto.BookingDTO;
import com.zyra.dto.PaymentDTO;
import com.zyra.dto.PaymentMethodDTO;
import com.zyra.service.PaymentService;
import com.zyra.service.BookingService;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(
            @Valid @RequestBody PaymentMethodDTO paymentRequestDTO,
            Principal principal) {
        return new ResponseEntity<>(
            paymentService.processPayment(paymentRequestDTO, principal.getName()), 
            HttpStatus.CREATED
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(
            @PathVariable Long id,
            Principal principal) {
        PaymentDTO payment = paymentService.getPaymentById(id);
        
        // Only allow payment owner or admin to access
        if (!payment.getUserEmail().equals(principal.getName()) && 
            !hasRole(principal, "ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByBookingId(
            @PathVariable Long bookingId,
            Principal principal) {
        // Verify booking ownership
        BookingDTO booking = bookingService.getBookingById(bookingId);
        if (!booking.getUserEmail().equals(principal.getName()) && 
            !hasRole(principal, "ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return new ResponseEntity<>(
            paymentService.getPaymentsByBookingId(bookingId), 
            HttpStatus.OK
        );
    }

    // Helper method to check roles
    private boolean hasRole(Principal principal, String role) {
        // Implementation would depend on how roles are stored
        // This should be implemented properly in a real application
        return true; // Placeholder
    }
}