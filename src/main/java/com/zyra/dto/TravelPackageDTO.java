package com.zyra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelPackageDTO {
    private Long id;
    
    @NotEmpty(message = "Package name should not be empty")
    private String name;
    
    @NotEmpty(message = "Description should not be empty")
    @Size(min = 10, message = "Description should have at least 10 characters")
    private String description;
    
    @NotNull(message = "Price should not be null")
    @Positive(message = "Price should be positive")
    private BigDecimal price;
    
    @NotNull(message = "Duration should not be null")
    @Positive(message = "Duration should be positive")
    private Integer duration;
    
    private String thumbnailUrl;
    private String bannerImageUrl;
    
    @Positive(message = "Minimum group size should be positive")
    private Integer groupSizeMin;
    
    @Positive(message = "Maximum group size should be positive")
    private Integer groupSizeMax;
    
    private boolean featured;
    private boolean active;
    
    private Double averageRating;
    private Integer reviewCount;
    
    private List<PackageAvailabilityDTO> availabilities;
    private List<ReviewDTO> reviews;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}