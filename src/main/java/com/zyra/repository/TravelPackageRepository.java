package com.zyra.repository;

import com.zyra.model.TravelPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {
    
    List<TravelPackage> findByFeaturedTrue();
    
    @Query("SELECT p FROM TravelPackage p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<TravelPackage> searchPackages(String keyword);
    
    Page<TravelPackage> findByActiveTrue(Pageable pageable);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.travelPackage.id = :packageId")
    Double getAverageRating(Long packageId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.travelPackage.id = :packageId")
    Integer getReviewCount(Long packageId);
    
    @Query("SELECT tp FROM TravelPackage tp WHERE tp.active = true AND tp.featured = true")
    List<TravelPackage> findFeaturedAndActivePackages();
    
    @Query("SELECT tp FROM TravelPackage tp WHERE tp.active = true AND tp.price BETWEEN :minPrice AND :maxPrice")
    Page<TravelPackage> findByPriceRange(@Param("minPrice") BigDecimal minPrice, 
                                         @Param("maxPrice") BigDecimal maxPrice, 
                                         Pageable pageable);
    
    @Query("SELECT tp FROM TravelPackage tp JOIN tp.destinations d WHERE tp.active = true AND d.id = :destinationId")
    Page<TravelPackage> findByDestinationId(@Param("destinationId") Long destinationId, Pageable pageable);
    
    @Query("SELECT tp FROM TravelPackage tp JOIN tp.destinations d WHERE tp.active = true AND d.country = :country")
    Page<TravelPackage> findByDestinationCountry(@Param("country") String country, Pageable pageable);
    
    @Query("SELECT tp FROM TravelPackage tp WHERE tp.active = true AND tp.duration <= :maxDuration")
    Page<TravelPackage> findByMaxDuration(@Param("maxDuration") Integer maxDuration, Pageable pageable);
    
    @Query("SELECT tp FROM TravelPackage tp WHERE tp.active = true AND " +
           "(LOWER(tp.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(tp.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<TravelPackage> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}