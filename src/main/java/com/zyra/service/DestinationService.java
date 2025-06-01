package com.zyra.service;

import com.zyra.dto.DestinationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DestinationService {
    DestinationDTO createDestination(DestinationDTO destinationDTO);
    DestinationDTO updateDestination(Long id, DestinationDTO destinationDTO);
    void deleteDestination(Long id);
    DestinationDTO getDestinationById(Long id);
    Page<DestinationDTO> getAllDestinations(Pageable pageable);
    Page<DestinationDTO> searchDestinations(String keyword, Pageable pageable);
    Page<DestinationDTO> getPopularDestinations(Pageable pageable);
}