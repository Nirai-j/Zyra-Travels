package com.zyra.service;

import com.zyra.dto.PaymentDTO;
import com.zyra.dto.PaymentMethodDTO;
import java.util.List;

public interface PaymentService {
    PaymentDTO processPayment(PaymentMethodDTO paymentMethod, String userEmail);
    PaymentDTO getPaymentById(Long id);
    List<PaymentDTO> getAllPayments();
    List<PaymentDTO> getPaymentsByBookingId(Long bookingId);
}
