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
public class DestinationDTO {
    private Long id;
    
    @NotEmpty(message = "Destination name should not be empty")
    private String name;
    
    @NotEmpty(message = "Description should not be empty")
    private String description;
    
    @NotEmpty(message = "Country should not be empty")
    private String country;
    
    private String region;
    private String city;
    private String imageUrl;
    private String featuredImageUrl;
    
    private boolean featured;
    private boolean active;
    
    // Geographical location
    private Double latitude;
    private Double longitude;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}