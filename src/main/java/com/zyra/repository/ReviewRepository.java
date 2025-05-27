package com.zyra.repository;

import com.zyra.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTravelPackageId(Long packageId);
    List<Review> findByUserId(Long userId);
    List<Review> findByTravelPackageIdAndApprovedTrue(Long packageId);
    List<Review> findByFeaturedTrue();
}
