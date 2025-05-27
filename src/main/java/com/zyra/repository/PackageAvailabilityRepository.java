package com.zyra.repository;

import com.zyra.model.PackageAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PackageAvailabilityRepository extends JpaRepository<PackageAvailability, Long> {
    List<PackageAvailability> findByTravelPackageId(Long packageId);
    
    List<PackageAvailability> findByTravelPackageIdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
            Long packageId, LocalDate startDate, LocalDate endDate);
    
    List<PackageAvailability> findByTravelPackageIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long packageId, LocalDate date, LocalDate sameDate);
    
    @Query("SELECT pa FROM PackageAvailability pa WHERE pa.travelPackage.id = :packageId " +
           "AND pa.startDate >= :startDate " +
           "AND pa.status <> com.zyra.model.PackageAvailability.AvailabilityStatus.SOLD_OUT " +
           "AND pa.status <> com.zyra.model.PackageAvailability.AvailabilityStatus.CANCELLED " +
           "ORDER BY pa.startDate")
    List<PackageAvailability> findAvailableByTravelPackageId(@Param("packageId") Long packageId,
                                                            @Param("startDate") LocalDate startDate);
    
    @Query("SELECT pa FROM PackageAvailability pa " +
           "WHERE pa.status <> com.zyra.model.PackageAvailability.AvailabilityStatus.SOLD_OUT " +
           "AND pa.status <> com.zyra.model.PackageAvailability.AvailabilityStatus.CANCELLED " +
           "AND pa.startDate BETWEEN :startDate AND :endDate " +
           "ORDER BY pa.startDate")
    List<PackageAvailability> findAvailableBetweenDates(@Param("startDate") LocalDate startDate,
                                                        @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(pa) FROM PackageAvailability pa " +
           "WHERE pa.travelPackage.id = :packageId AND pa.capacity > pa.bookedCount " +
           "AND pa.startDate >= CURRENT_DATE")
    Long countAvailableSlots(@Param("packageId") Long packageId);
    
    void deleteByTravelPackageId(Long travelPackageId);
}