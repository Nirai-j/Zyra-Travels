package com.zyra.repository;

import com.zyra.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    
    List<Destination> findByFeaturedTrue();
    
    List<Destination> findByActiveTrue();
    
    List<Destination> findByCountry(String country);
    
    List<Destination> findByRegion(String region);
    
    @Query("SELECT DISTINCT d.country FROM Destination d ORDER BY d.country")
    List<String> findAllCountries();
    
    @Query("SELECT DISTINCT d.region FROM Destination d WHERE d.country = ?1 ORDER BY d.region")
    List<String> findRegionsByCountry(String country);
    
    @Query("SELECT d FROM Destination d WHERE d.active = true AND d.featured = true")
    List<Destination> findFeaturedAndActiveDestinations();
}