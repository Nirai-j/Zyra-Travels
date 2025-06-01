package com.zyra.service.Impl;

import com.zyra.dto.PaymentDTO;
import com.zyra.dto.PaymentMethodDTO;
import com.zyra.exception.ResourceNotFoundException;
import com.zyra.model.Booking;
import com.zyra.model.Payment;
import com.zyra.model.PaymentMethod;
import com.zyra.repository.BookingRepository;
import com.zyra.repository.PaymentMethodRepository;
import com.zyra.repository.PaymentRepository;
import com.zyra.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    @Transactional
    public PaymentDTO processPayment(PaymentMethodDTO paymentMethodDTO, String bookingId) {
        Booking booking = bookingRepository.findByBookingNumber(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodDTO.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Payment method not found"));

        Payment payment = Payment.builder()
            .booking(booking)
            .paymentMethod(paymentMethod)
            .amount(booking.getTotalAmount())
            .status(Payment.PaymentStatus.PENDING)
            .currency("USD")
            .createdAt(LocalDateTime.now())
            .build();

        payment = paymentRepository.save(payment);
        return mapToDTO(payment);
    }

    @Override
    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        return mapToDTO(payment);
    }

    @Override
    public List<PaymentDTO> getPaymentsByBookingId(Long bookingId) {
        List<Payment> payments = paymentRepository.findByBookingId(bookingId);
        return payments.stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
            .map(this::mapToDTO);
    }

    private PaymentDTO mapToDTO(Payment payment) {
        return PaymentDTO.builder()
            .id(payment.getId())
            .bookingId(payment.getBooking().getId())
            .paymentMethodId(payment.getPaymentMethod().getId())
            .paymentMethodName(payment.getPaymentMethod().getName())
            .amount(payment.getAmount())
            .status(payment.getStatus())
            .currency(payment.getCurrency())
            .transactionId(payment.getTransactionId())
            .paymentGateway(payment.getPaymentGateway())
            .errorMessage(payment.getErrorMessage())
            .receiptUrl(payment.getReceiptUrl())
            .notes(payment.getNotes())
            .createdAt(payment.getCreatedAt())
            .updatedAt(payment.getUpdatedAt())
            .build();
    }
}
