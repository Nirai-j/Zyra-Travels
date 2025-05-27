package com.zyra.repository;

import com.zyra.model.ItineraryDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryDayRepository extends JpaRepository<ItineraryDay, Long> {
    
    List<ItineraryDay> findByTravelPackageIdOrderByDayNumber(Long travelPackageId);
    
    void deleteByTravelPackageId(Long travelPackageId);
}