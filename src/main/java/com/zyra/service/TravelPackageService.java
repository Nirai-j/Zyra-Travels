package com.zyra.service;

import com.zyra.dto.PackageAvailabilityDTO;
import com.zyra.dto.ReviewDTO;
import com.zyra.dto.TravelPackageDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TravelPackageService {
    TravelPackageDTO createPackage(TravelPackageDTO packageDTO);
    TravelPackageDTO updatePackage(Long id, TravelPackageDTO packageDTO);
    TravelPackageDTO getPackageById(Long id);
    void deletePackage(Long id);
    Map<String, Object> getAllPackages(int page, int size, String sortBy, String sortDir);
    List<TravelPackageDTO> getFeaturedPackages();
    List<TravelPackageDTO> searchPackages(String keyword);
    
    // Availability management
    PackageAvailabilityDTO addAvailability(Long packageId, PackageAvailabilityDTO availabilityDTO);
    PackageAvailabilityDTO updateAvailability(Long packageId, Long availabilityId, PackageAvailabilityDTO availabilityDTO);
    void deleteAvailability(Long packageId, Long availabilityId);
    List<PackageAvailabilityDTO> getAvailabilities(Long packageId, LocalDate startDate, LocalDate endDate);
    
    // Review management
    ReviewDTO addReview(Long packageId, ReviewDTO reviewDTO);
    ReviewDTO updateReview(Long packageId, Long reviewId, ReviewDTO reviewDTO);
    void deleteReview(Long packageId, Long reviewId);
    List<ReviewDTO> getPackageReviews(Long packageId);
    
    // Statistics and metrics
    Map<String, Object> getPackageStats(Long packageId);
    void updatePackageRating(Long packageId);
    boolean checkAvailability(Long packageId, LocalDate date, Integer numberOfTravelers);
}
