package com.zyra.controller;

import com.zyra.dto.PackageAvailabilityDTO;
import com.zyra.dto.TravelPackageDTO;
import com.zyra.service.TravelPackageService;
import com.zyra.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/packages")
public class TravelPackageController {

    private final TravelPackageService packageService;

    @Autowired
    public TravelPackageController(TravelPackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TravelPackageDTO> createPackage(@Valid @RequestBody TravelPackageDTO packageDTO) {
        return new ResponseEntity<>(packageService.createPackage(packageDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPackages(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDir) {
        return ResponseEntity.ok(packageService.getAllPackages(page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelPackageDTO> getPackageById(@PathVariable Long id) {
        return ResponseEntity.ok(packageService.getPackageById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TravelPackageDTO> updatePackage(
            @PathVariable Long id,
            @Valid @RequestBody TravelPackageDTO packageDTO) {
        return ResponseEntity.ok(packageService.updatePackage(id, packageDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        packageService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/featured")
    public ResponseEntity<List<TravelPackageDTO>> getFeaturedPackages() {
        return ResponseEntity.ok(packageService.getFeaturedPackages());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TravelPackageDTO>> searchPackages(@RequestParam String keyword) {
        return ResponseEntity.ok(packageService.searchPackages(keyword));
    }

    // Availability endpoints
    @PostMapping("/{packageId}/availabilities")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PackageAvailabilityDTO> addAvailability(
            @PathVariable Long packageId,
            @Valid @RequestBody PackageAvailabilityDTO availabilityDTO) {
        return new ResponseEntity<>(packageService.addAvailability(packageId, availabilityDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{packageId}/availabilities")
    public ResponseEntity<List<PackageAvailabilityDTO>> getAvailabilities(
            @PathVariable Long packageId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(packageService.getAvailabilities(packageId, startDate, endDate));
    }

    @PutMapping("/{packageId}/availabilities/{availabilityId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PackageAvailabilityDTO> updateAvailability(
            @PathVariable Long packageId,
            @PathVariable Long availabilityId,
            @Valid @RequestBody PackageAvailabilityDTO availabilityDTO) {
        return ResponseEntity.ok(packageService.updateAvailability(packageId, availabilityId, availabilityDTO));
    }

    @DeleteMapping("/{packageId}/availabilities/{availabilityId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAvailability(
            @PathVariable Long packageId,
            @PathVariable Long availabilityId) {
        packageService.deleteAvailability(packageId, availabilityId);
        return ResponseEntity.noContent().build();
    }

    // Statistics endpoint
    @GetMapping("/{packageId}/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getPackageStats(@PathVariable Long packageId) {
        return ResponseEntity.ok(packageService.getPackageStats(packageId));
    }
}
