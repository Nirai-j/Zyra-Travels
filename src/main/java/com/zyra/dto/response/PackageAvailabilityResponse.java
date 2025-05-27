package com.zyra.dto.response;

import com.zyra.model.PackageAvailability.AvailabilityStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageAvailabilityResponse {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer capacity;
    private Integer bookedCount;
    private BigDecimal specialPrice;
    private AvailabilityStatus status;
}
