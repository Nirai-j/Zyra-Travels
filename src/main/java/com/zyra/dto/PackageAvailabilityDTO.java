package com.zyra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackageAvailabilityDTO {
    private Long id;
    
    @NotNull(message = "Travel package ID should not be null")
    private Long travelPackageId;
    
    @NotNull(message = "Start date should not be null")
    private LocalDate startDate;
    
    @NotNull(message = "End date should not be null")
    private LocalDate endDate;
    
    @NotNull(message = "Capacity should not be null")
    @Positive(message = "Capacity should be positive")
    private Integer capacity;
    
    private Integer bookedCount;
    private BigDecimal specialPrice;
    
    private String status; // AVAILABLE, FULLY_BOOKED, CANCELLED
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}