package com.zyra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackageAmenityDTO {
    private Long id;
    
    private Long travelPackageId;
    
    @NotEmpty(message = "Amenity name should not be empty")
    private String name;
    
    private String description;
    private String iconName;
    private boolean included;
}