package com.zyra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDTO {
    private Long id;
    
    @NotEmpty(message = "Payment method name should not be empty")
    private String name;
    
    private String description;
    private boolean active;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}