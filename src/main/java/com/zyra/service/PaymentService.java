package com.zyra.service;

import com.zyra.dto.PaymentDTO;
import com.zyra.dto.PaymentMethodDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {
    PaymentDTO processPayment(PaymentMethodDTO paymentMethod, String bookingId);
    PaymentDTO getPaymentById(Long id);
    List<PaymentDTO> getPaymentsByBookingId(Long bookingId);
    Page<PaymentDTO> getAllPayments(Pageable pageable);
}
