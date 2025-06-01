package com.zyra.service.Impl;

import com.zyra.dto.ReviewDTO;
import com.zyra.model.Review;
import com.zyra.repository.ReviewRepository;
import com.zyra.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = mapToEntity(reviewDTO);
        Review savedReview = reviewRepository.save(review);
        return mapToDTO(savedReview);
    }

    @Override
    public ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        updateReviewFromDTO(review, reviewDTO);
        Review updatedReview = reviewRepository.save(review);
        return mapToDTO(updatedReview);
    }

    @Override
    public ReviewDTO getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return mapToDTO(review);
    }

    @Override
    public List<ReviewDTO> getReviewsByPackageId(Long packageId) {
        return reviewRepository.findByTravelPackageId(packageId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getFeaturedReviews() {
        return reviewRepository.findByFeaturedTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReviewDTO> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(this::mapToDTO);
    }

    @Override
    public Page<ReviewDTO> getApprovedReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable)
                .map(this::mapToDTO)
                .map(dto -> {
                    if (dto.isApproved()) return dto;
                    return null;
                });
    }

    @Override
    public ReviewDTO approveReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setApproved(true);
        return mapToDTO(reviewRepository.save(review));
    }

    @Override
    public ReviewDTO rejectReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setApproved(false);
        return mapToDTO(reviewRepository.save(review));
    }

    @Override
    public ReviewDTO toggleFeatured(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setFeatured(!review.isFeatured());
        return mapToDTO(reviewRepository.save(review));
    }

    @Override
    public void addHelpfulVote(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.incrementHelpfulVotes();
        reviewRepository.save(review);
    }

    @Override
    public void reportReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.incrementReportCount();
        reviewRepository.save(review);
    }

    @Override
    public Double getAverageRatingForPackage(Long packageId) {
        return reviewRepository.findByTravelPackageId(packageId).stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    @Override
    public Long getReviewCountForPackage(Long packageId) {
        return (long) reviewRepository.findByTravelPackageId(packageId).size();
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    // Helper methods
    private Review mapToEntity(ReviewDTO dto) {
        Review review = new Review();
        updateReviewFromDTO(review, dto);
        return review;
    }

    private void updateReviewFromDTO(Review review, ReviewDTO dto) {
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        if (dto.getAdminResponse() != null) {
            review.setAdminResponse(dto.getAdminResponse());
        }
        review.setApproved(dto.isApproved());
        review.setFeatured(dto.isFeatured());
    }

    private ReviewDTO mapToDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .bookingId(review.getBooking() != null ? review.getBooking().getId() : null)
                .userId(review.getUser() != null ? review.getUser().getId() : null)
                .userName(review.getUser() != null ? review.getUser().getUsername() : null)
                .travelPackageId(review.getTravelPackage() != null ? review.getTravelPackage().getId() : null)
                .travelPackageName(review.getTravelPackage() != null ? review.getTravelPackage().getName() : null)
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
}
