package com.zyra.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "travel_packages")
@EntityListeners(AuditingEntityListener.class)
public class TravelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Column(length = 2000)
    private String description;
    
    private BigDecimal price;
    private Integer duration; // in days
    private String thumbnailUrl;
    private String bannerImageUrl;
    
    @Column(name = "group_size_min")
    private Integer groupSizeMin;
    
    @Column(name = "group_size_max")
    private Integer groupSizeMax;
    
    @Column(name = "is_featured")
    private boolean featured;
    
    @Column(name = "is_active")
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "package_destinations",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "destination_id")
    )
    private Set<Destination> destinations = new HashSet<>();

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItineraryDay> itineraryDays = new HashSet<>();

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PackageAmenity> amenities = new HashSet<>();

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PackageAvailability> availabilities = new HashSet<>();

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL)
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();

    @Column(name = "avg_rating")
    private Double averageRating;

    @Column(name = "review_count")
    private Integer reviewCount;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}