package com.zyra.repository;

import com.zyra.model.PackageAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageAmenityRepository extends JpaRepository<PackageAmenity, Long> {
    
    List<PackageAmenity> findByTravelPackageId(Long travelPackageId);
    
    List<PackageAmenity> findByTravelPackageIdAndIncludedTrue(Long travelPackageId);
    
    void deleteByTravelPackageId(Long travelPackageId);
}