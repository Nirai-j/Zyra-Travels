package com.zyra.service.Impl;

import com.zyra.dto.PackageAvailabilityDTO;
import com.zyra.dto.ReviewDTO;
import com.zyra.dto.TravelPackageDTO;
import com.zyra.exception.ResourceNotFoundException;
import com.zyra.model.PackageAvailability;
import com.zyra.model.Review;
import com.zyra.model.TravelPackage;
import com.zyra.repository.PackageAvailabilityRepository;
import com.zyra.repository.ReviewRepository;
import com.zyra.repository.TravelPackageRepository;
import com.zyra.service.TravelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class TravelPackageServiceImpl implements TravelPackageService {

    @Autowired
    private TravelPackageRepository packageRepository;

    @Autowired
    private PackageAvailabilityRepository availabilityRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public TravelPackageDTO createPackage(TravelPackageDTO packageDTO) {
        TravelPackage travelPackage = mapToEntity(packageDTO);
        TravelPackage savedPackage = packageRepository.save(travelPackage);
        return mapToDTO(savedPackage);
    }

    @Override
    public TravelPackageDTO updatePackage(Long id, TravelPackageDTO packageDTO) {
        TravelPackage travelPackage = packageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Travel Package", "id", id));
        
        updatePackageFromDTO(travelPackage, packageDTO);
        TravelPackage updatedPackage = packageRepository.save(travelPackage);
        return mapToDTO(updatedPackage);
    }

    @Override
    public TravelPackageDTO getPackageById(Long id) {
        TravelPackage travelPackage = packageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Travel Package", "id", id));
        return mapToDTO(travelPackage);
    }

    @Override
    public void deletePackage(Long id) {
        TravelPackage travelPackage = packageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Travel Package", "id", id));
        packageRepository.delete(travelPackage);
    }

    @Override
    public Map<String, Object> getAllPackages(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        
        Page<TravelPackage> packages = packageRepository.findByActiveTrue(PageRequest.of(page, size, sort));
        
        Map<String, Object> response = new HashMap<>();
        response.put("content", packages.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList()));
        response.put("currentPage", packages.getNumber());
        response.put("totalItems", packages.getTotalElements());
        response.put("totalPages", packages.getTotalPages());
        
        return response;
    }

    @Override
    public List<TravelPackageDTO> getFeaturedPackages() {
        return packageRepository.findByFeaturedTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TravelPackageDTO> searchPackages(String keyword) {
        return packageRepository.searchPackages(keyword).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PackageAvailabilityDTO addAvailability(Long packageId, PackageAvailabilityDTO availabilityDTO) {
        TravelPackage travelPackage = packageRepository.findById(packageId)
                .orElseThrow(() -> new ResourceNotFoundException("Travel Package", "id", packageId));
        
        PackageAvailability availability = mapToAvailabilityEntity(availabilityDTO);
        availability.setTravelPackage(travelPackage);
        
        PackageAvailability savedAvailability = availabilityRepository.save(availability);
        return mapToAvailabilityDTO(savedAvailability);
    }

    @Override
    public PackageAvailabilityDTO updateAvailability(Long packageId, Long availabilityId, PackageAvailabilityDTO availabilityDTO) {
        PackageAvailability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Availability", "id", availabilityId));
        
        if (!availability.getTravelPackage().getId().equals(packageId)) {
            throw new ResourceNotFoundException("Availability not found for package id: " + packageId);
        }
        
        updateAvailabilityFromDTO(availability, availabilityDTO);
        PackageAvailability updatedAvailability = availabilityRepository.save(availability);
        return mapToAvailabilityDTO(updatedAvailability);
    }

    @Override
    public void deleteAvailability(Long packageId, Long availabilityId) {
        PackageAvailability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Availability", "id", availabilityId));
        
        if (!availability.getTravelPackage().getId().equals(packageId)) {
            throw new ResourceNotFoundException("Availability not found for package id: " + packageId);
        }
        
        availabilityRepository.delete(availability);
    }

    @Override
    public List<PackageAvailabilityDTO> getAvailabilities(Long packageId, LocalDate startDate, LocalDate endDate) {
        return availabilityRepository.findByTravelPackageIdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
                packageId, startDate, endDate)
                .stream()
                .map(this::mapToAvailabilityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO addReview(Long packageId, ReviewDTO reviewDTO) {
        TravelPackage travelPackage = packageRepository.findById(packageId)
                .orElseThrow(() -> new ResourceNotFoundException("Travel Package", "id", packageId));
        
        Review review = mapToReviewEntity(reviewDTO);
        review.setTravelPackage(travelPackage);
        
        Review savedReview = reviewRepository.save(review);
        updatePackageRating(packageId);
        
        return mapToReviewDTO(savedReview);
    }

    @Override
    public ReviewDTO updateReview(Long packageId, Long reviewId, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));
        
        if (!review.getTravelPackage().getId().equals(packageId)) {
            throw new ResourceNotFoundException("Review not found for package id: " + packageId);
        }
        
        updateReviewFromDTO(review, reviewDTO);
        Review updatedReview = reviewRepository.save(review);
        updatePackageRating(packageId);
        
        return mapToReviewDTO(updatedReview);
    }

    @Override
    public void deleteReview(Long packageId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));
        
        if (!review.getTravelPackage().getId().equals(packageId)) {
            throw new ResourceNotFoundException("Review not found for package id: " + packageId);
        }
        
        reviewRepository.delete(review);
        updatePackageRating(packageId);
    }

    @Override
    public List<ReviewDTO> getPackageReviews(Long packageId) {
        return reviewRepository.findByTravelPackageId(packageId)
                .stream()
                .map(this::mapToReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getPackageStats(Long packageId) {
        TravelPackage travelPackage = packageRepository.findById(packageId)
                .orElseThrow(() -> new ResourceNotFoundException("Travel Package", "id", packageId));
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("averageRating", packageRepository.getAverageRating(packageId));
        stats.put("reviewCount", packageRepository.getReviewCount(packageId));
        // Add more statistics as needed
        
        return stats;
    }

    @Override
    public void updatePackageRating(Long packageId) {
        Double avgRating = packageRepository.getAverageRating(packageId);
        Integer reviewCount = packageRepository.getReviewCount(packageId);
        
        TravelPackage travelPackage = packageRepository.findById(packageId)
                .orElseThrow(() -> new ResourceNotFoundException("Travel Package", "id", packageId));
        
        travelPackage.setAverageRating(avgRating);
        travelPackage.setReviewCount(reviewCount);
        packageRepository.save(travelPackage);
    }

    @Override
    public boolean checkAvailability(Long packageId, LocalDate date, Integer numberOfTravelers) {
        List<PackageAvailability> availabilities = availabilityRepository
                .findByTravelPackageIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(packageId, date, date);
        
        return availabilities.stream()
                .anyMatch(a -> (a.getCapacity() - a.getBookedCount()) >= numberOfTravelers);
    }

    // Helper methods for entity/DTO conversion
    private TravelPackageDTO mapToDTO(TravelPackage travelPackage) {
        return TravelPackageDTO.builder()
                .id(travelPackage.getId())
                .name(travelPackage.getName())
                .description(travelPackage.getDescription())
                .price(travelPackage.getPrice())
                .duration(travelPackage.getDuration())
                .thumbnailUrl(travelPackage.getThumbnailUrl())
                .bannerImageUrl(travelPackage.getBannerImageUrl())
                .groupSizeMin(travelPackage.getGroupSizeMin())
                .groupSizeMax(travelPackage.getGroupSizeMax())
                .featured(travelPackage.isFeatured())
                .active(travelPackage.isActive())
                .averageRating(travelPackage.getAverageRating())
                .reviewCount(travelPackage.getReviewCount())
                .createdAt(travelPackage.getCreatedAt())
                .updatedAt(travelPackage.getUpdatedAt())
                .build();
    }

    private TravelPackage mapToEntity(TravelPackageDTO packageDTO) {
        return TravelPackage.builder()
                .name(packageDTO.getName())
                .description(packageDTO.getDescription())
                .price(packageDTO.getPrice())
                .duration(packageDTO.getDuration())
                .thumbnailUrl(packageDTO.getThumbnailUrl())
                .bannerImageUrl(packageDTO.getBannerImageUrl())
                .groupSizeMin(packageDTO.getGroupSizeMin())
                .groupSizeMax(packageDTO.getGroupSizeMax())
                .featured(packageDTO.isFeatured())
                .active(packageDTO.isActive())
                .build();
    }

    private void updatePackageFromDTO(TravelPackage travelPackage, TravelPackageDTO packageDTO) {
        travelPackage.setName(packageDTO.getName());
        travelPackage.setDescription(packageDTO.getDescription());
        travelPackage.setPrice(packageDTO.getPrice());
        travelPackage.setDuration(packageDTO.getDuration());
        travelPackage.setThumbnailUrl(packageDTO.getThumbnailUrl());
        travelPackage.setBannerImageUrl(packageDTO.getBannerImageUrl());
        travelPackage.setGroupSizeMin(packageDTO.getGroupSizeMin());
        travelPackage.setGroupSizeMax(packageDTO.getGroupSizeMax());
        travelPackage.setFeatured(packageDTO.isFeatured());
        travelPackage.setActive(packageDTO.isActive());
    }

    // Similar mapping methods for Availability and Review
    private PackageAvailabilityDTO mapToAvailabilityDTO(PackageAvailability availability) {
        return PackageAvailabilityDTO.builder()
                .id(availability.getId())
                .travelPackageId(availability.getTravelPackage().getId())
                .startDate(availability.getStartDate())
                .endDate(availability.getEndDate())
                .capacity(availability.getCapacity())
                .bookedCount(availability.getBookedCount())
                .specialPrice(availability.getSpecialPrice())
                .status(availability.getStatus().toString())
                .createdAt(availability.getCreatedAt())
                .updatedAt(availability.getUpdatedAt())
                .build();
    }

    private PackageAvailability mapToAvailabilityEntity(PackageAvailabilityDTO dto) {
        return PackageAvailability.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .capacity(dto.getCapacity())
                .bookedCount(dto.getBookedCount())
                .specialPrice(dto.getSpecialPrice())
                .status(PackageAvailability.AvailabilityStatus.valueOf(dto.getStatus()))
                .build();
    }

    private ReviewDTO mapToReviewDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .bookingId(review.getBooking().getId())
                .userId(review.getUser().getId())
                .userName(review.getUser().getUsername())
                .travelPackageId(review.getTravelPackage().getId())
                .travelPackageName(review.getTravelPackage().getName())
                .rating(review.getRating())
                .comment(review.getComment())
                .adminResponse(review.getAdminResponse())
                .approved(review.isApproved())
                .helpfulVotes(review.getHelpfulVotes())
                .reportCount(review.getReportCount())
                .featured(review.isFeatured())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

    private Review mapToReviewEntity(ReviewDTO dto) {
        Review review = new Review();
        updateReviewFromDTO(review, dto);
        return review;
    }

    private void updateReviewFromDTO(Review review, ReviewDTO dto) {
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setAdminResponse(dto.getAdminResponse());
        review.setApproved(dto.isApproved());
        review.setFeatured(dto.isFeatured());
    }

    private void updateAvailabilityFromDTO(PackageAvailability availability, PackageAvailabilityDTO dto) {
        availability.setStartDate(dto.getStartDate());
        availability.setEndDate(dto.getEndDate());
        availability.setCapacity(dto.getCapacity());
        availability.setBookedCount(dto.getBookedCount());
        availability.setSpecialPrice(dto.getSpecialPrice());
        availability.setStatus(PackageAvailability.AvailabilityStatus.valueOf(dto.getStatus()));
    }
}
