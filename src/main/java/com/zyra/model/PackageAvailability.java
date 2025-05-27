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
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "package_availabilities")
@EntityListeners(AuditingEntityListener.class)
public class PackageAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_package_id", nullable = false)
    private TravelPackage travelPackage;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer capacity;
    private Integer bookedCount;
    
    private BigDecimal specialPrice; // Optional special price for this date range
    
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus status;
    
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public enum AvailabilityStatus {
        AVAILABLE,
        FILLING_FAST,
        ALMOST_FULL,
        SOLD_OUT,
        CANCELLED
    }
}