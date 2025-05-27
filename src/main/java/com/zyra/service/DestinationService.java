package com.zyra.service;

import com.zyra.dto.DestinationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DestinationService {
    DestinationDTO createDestination(DestinationDTO destinationDto);
    DestinationDTO getDestinationById(Long id);
    Page<DestinationDTO> searchDestinations(String searchTerm, Pageable pageable);
    DestinationDTO updateDestination(Long id, DestinationDTO destinationDto);
    void deleteDestination(Long id);
    List<DestinationDTO> getActiveDestinations();
}