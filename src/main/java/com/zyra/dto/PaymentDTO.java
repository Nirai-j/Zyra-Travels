package com.zyra.dto;

import com.zyra.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long id;
    
    @NotNull(message = "Booking ID should not be null")
    private Long bookingId;
    
    private Long paymentMethodId;
    private String paymentMethodName;
    
    private String transactionId;
    
    @NotNull(message = "Amount should not be null")
    @Positive(message = "Amount should be positive")
    private BigDecimal amount;
    
    private Payment.PaymentStatus status;
    private String currency;
    private String paymentGateway;
    private String errorMessage;
    private String receiptUrl;
    private String notes;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String userEmail;
}