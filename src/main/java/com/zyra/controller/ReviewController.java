package com.zyra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.zyra.dto.ReviewDTO;
import com.zyra.service.ReviewService;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/package/{packageId}")
    public ResponseEntity<ReviewDTO> createReview(
            @PathVariable Long packageId,
            @Valid @RequestBody ReviewDTO reviewDTO,
            Principal principal) {
        reviewDTO.setTravelPackageId(packageId);
        reviewDTO.setUserId(getUserId(principal));
        return new ResponseEntity<>(reviewService.createReview(reviewDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ReviewDTO>> getAllReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return ResponseEntity.ok(reviewService.getAllReviews(PageRequest.of(page, size, sort)));
    }

    @GetMapping("/approved")
    public ResponseEntity<Page<ReviewDTO>> getApprovedReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return ResponseEntity.ok(reviewService.getApprovedReviews(PageRequest.of(page, size, sort)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/package/{packageId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByPackageId(@PathVariable Long packageId) {
        return ResponseEntity.ok(reviewService.getReviewsByPackageId(packageId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    @GetMapping("/featured")
    public ResponseEntity<List<ReviewDTO>> getFeaturedReviews() {
        return ResponseEntity.ok(reviewService.getFeaturedReviews());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewDTO reviewDTO,
            Principal principal) {
        if (!isReviewOwner(id, principal) && !isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(reviewService.updateReview(id, reviewDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long id,
            Principal principal) {
        if (!isReviewOwner(id, principal) && !isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReviewDTO> approveReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.approveReview(id));
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReviewDTO> rejectReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.rejectReview(id));
    }

    @PutMapping("/{id}/toggle-featured")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReviewDTO> toggleFeatured(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.toggleFeatured(id));
    }

    @PostMapping("/{id}/helpful")
    public ResponseEntity<Void> addHelpfulVote(@PathVariable Long id) {
        reviewService.addHelpfulVote(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/report")
    public ResponseEntity<Void> reportReview(@PathVariable Long id) {
        reviewService.reportReview(id);
        return ResponseEntity.ok().build();
    }

    // Helper methods
    private Long getUserId(Principal principal) {
        // Implementation would depend on how user information is stored
        // This should be implemented properly in a real application
        return 1L;
    }

    private boolean isReviewOwner(Long reviewId, Principal principal) {
        // Implementation would depend on how reviews are stored
        // This should be implemented properly in a real application
        return true;
    }

    private boolean isAdmin(Principal principal) {
        // Implementation would depend on how roles are stored
        // This should be implemented properly in a real application
        return true;
    }
}