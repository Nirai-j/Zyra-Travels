package com.zyra.dto;

import com.zyra.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long id;
    private String bookingNumber;
    
    private Long userId;
    private String userName;
    private String userEmail;
    
    @NotNull(message = "Travel package ID should not be null")
    private Long travelPackageId;
    private String travelPackageName;
    
    @NotNull(message = "Availability ID should not be null")
    private Long availabilityId;
    
    @NotNull(message = "Start date should not be null")
    private LocalDate startDate;
    
    @NotNull(message = "End date should not be null")
    private LocalDate endDate;
    
    @NotNull(message = "Number of travelers should not be null")
    @Positive(message = "Number of travelers should be positive")
    private Integer numberOfTravelers;
    
    private BigDecimal totalAmount;
    
    @NotEmpty(message = "Contact email should not be empty")
    @Email(message = "Contact email should be valid")
    private String contactEmail;
    
    private String contactPhone;
    private String specialRequests;
    
    private List<PaymentDTO> payments;
    private Booking.BookingStatus status;
    
    private Boolean reminderSent;
    private Boolean feedbackRequestSent;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}