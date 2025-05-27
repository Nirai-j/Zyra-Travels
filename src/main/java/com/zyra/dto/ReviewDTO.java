package com.zyra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    
    private Long bookingId;
    private Long userId;
    private String userName;
    
    @NotNull(message = "Travel package ID should not be null")
    private Long travelPackageId;
    private String travelPackageName;
    
    @NotNull(message = "Rating should not be null")
    @Min(value = 1, message = "Rating should be at least 1")
    @Max(value = 5, message = "Rating should be at most 5")
    private Integer rating;
    
    @NotEmpty(message = "Review comment should not be empty")
    private String comment;
    
    private String adminResponse;
    private boolean approved;
    private Integer helpfulVotes;
    private Integer reportCount;
    private boolean featured;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}