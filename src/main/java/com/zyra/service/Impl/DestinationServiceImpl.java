package com.zyra.service.Impl;

import com.zyra.dto.DestinationDTO;
import com.zyra.exception.ResourceNotFoundException;
import com.zyra.model.Destination;
import com.zyra.repository.DestinationRepository;
import com.zyra.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DestinationServiceImpl implements DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    @Override
    public DestinationDTO createDestination(DestinationDTO destinationDTO) {
        Destination destination = mapToEntity(destinationDTO);
        Destination savedDestination = destinationRepository.save(destination);
        return mapToDTO(savedDestination);
    }

    @Override
    public DestinationDTO updateDestination(Long id, DestinationDTO destinationDTO) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination", "id", id));
        
        updateDestinationFromDTO(destination, destinationDTO);
        Destination updatedDestination = destinationRepository.save(destination);
        return mapToDTO(updatedDestination);
    }

    @Override
    public void deleteDestination(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination", "id", id));
        destinationRepository.delete(destination);
    }

    @Override
    public DestinationDTO getDestinationById(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination", "id", id));
        return mapToDTO(destination);
    }

    @Override
    public Page<DestinationDTO> getAllDestinations(Pageable pageable) {
        Page<Destination> destinations = destinationRepository.findAll(pageable);
        List<DestinationDTO> destinationDTOs = destinations.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        
        return new PageImpl<>(destinationDTOs, pageable, destinations.getTotalElements());
    }

    @Override
    public Page<DestinationDTO> searchDestinations(String keyword, Pageable pageable) {
        // For now, return all destinations - you can implement search logic later
        return getAllDestinations(pageable);
    }

    @Override
    public Page<DestinationDTO> getPopularDestinations(Pageable pageable) {
        // For now, return featured destinations
        List<Destination> featuredDestinations = destinationRepository.findByFeaturedTrue();
        List<DestinationDTO> destinationDTOs = featuredDestinations.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        
        return new PageImpl<>(destinationDTOs, pageable, featuredDestinations.size());
    }

    private DestinationDTO mapToDTO(Destination destination) {
        return DestinationDTO.builder()
                .id(destination.getId())
                .name(destination.getName())
                .description(destination.getDescription())
                .country(destination.getCountry())
                .region(destination.getRegion())
                .city(destination.getCity())
                .imageUrl(destination.getImageUrl())
                .featuredImageUrl(destination.getFeaturedImageUrl())
                .featured(destination.isFeatured())
                .active(destination.isActive())
                .latitude(destination.getLatitude())
                .longitude(destination.getLongitude())
                .createdAt(destination.getCreatedAt())
                .updatedAt(destination.getUpdatedAt())
                .build();
    }

    private Destination mapToEntity(DestinationDTO destinationDTO) {
        return Destination.builder()
                .name(destinationDTO.getName())
                .description(destinationDTO.getDescription())
                .country(destinationDTO.getCountry())
                .region(destinationDTO.getRegion())
                .city(destinationDTO.getCity())
                .imageUrl(destinationDTO.getImageUrl())
                .featuredImageUrl(destinationDTO.getFeaturedImageUrl())
                .featured(destinationDTO.isFeatured())
                .active(destinationDTO.isActive())
                .latitude(destinationDTO.getLatitude())
                .longitude(destinationDTO.getLongitude())
                .build();
    }

    private void updateDestinationFromDTO(Destination destination, DestinationDTO destinationDTO) {
        destination.setName(destinationDTO.getName());
        destination.setDescription(destinationDTO.getDescription());
        destination.setCountry(destinationDTO.getCountry());
        destination.setRegion(destinationDTO.getRegion());
        destination.setCity(destinationDTO.getCity());
        destination.setImageUrl(destinationDTO.getImageUrl());
        destination.setFeaturedImageUrl(destinationDTO.getFeaturedImageUrl());
        destination.setFeatured(destinationDTO.isFeatured());
        destination.setActive(destinationDTO.isActive());
        destination.setLatitude(destinationDTO.getLatitude());
        destination.setLongitude(destinationDTO.getLongitude());
    }
} 