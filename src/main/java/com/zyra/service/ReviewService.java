package com.zyra.service;

import com.zyra.dto.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(ReviewDTO reviewDTO);
    ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO);
    ReviewDTO getReviewById(Long reviewId);
    void deleteReview(Long reviewId);
    
    List<ReviewDTO> getReviewsByPackageId(Long packageId);
    List<ReviewDTO> getReviewsByUserId(Long userId);
    List<ReviewDTO> getFeaturedReviews();
    
    Page<ReviewDTO> getAllReviews(Pageable pageable);
    Page<ReviewDTO> getApprovedReviews(Pageable pageable);
    
    ReviewDTO approveReview(Long reviewId);
    ReviewDTO rejectReview(Long reviewId);
    ReviewDTO toggleFeatured(Long reviewId);
    
    void addHelpfulVote(Long reviewId);
    void reportReview(Long reviewId);
    
    Double getAverageRatingForPackage(Long packageId);
    Long getReviewCountForPackage(Long packageId);
}
