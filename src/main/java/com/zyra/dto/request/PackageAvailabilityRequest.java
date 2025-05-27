package com.zyra.dto.request;

import com.zyra.model.PackageAvailability.AvailabilityStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageAvailabilityRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer capacity;
    private BigDecimal specialPrice;
    private AvailabilityStatus status;
}
