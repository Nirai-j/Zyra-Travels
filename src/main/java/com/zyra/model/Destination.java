package com.zyra.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "destinations")
@EntityListeners(AuditingEntityListener.class)
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Column(length = 1000)
    private String description;
    
    private String country;
    private String region;
    private String city;
    private String imageUrl;
    private String featuredImageUrl;
    
    @Column(name = "is_featured")
    private boolean featured;
    
    @Column(name = "is_active")
    private boolean active;
    
    // Geographical location
    private Double latitude;
    private Double longitude;

    @ManyToMany(mappedBy = "destinations")
    private Set<TravelPackage> travelPackages = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}