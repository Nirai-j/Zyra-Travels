package com.zyra.service.Impl;

import com.zyra.dto.BookingDTO;
import com.zyra.exception.ResourceNotFoundException;
import com.zyra.model.Booking;
import com.zyra.repository.BookingRepository;
import com.zyra.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking = mapToEntity(bookingDTO);
        Booking savedBooking = bookingRepository.save(booking);
        return mapToDTO(savedBooking);
    }

    @Override
    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
        return mapToDTO(booking);
    }

    @Override
    public Map<String, Object> getAllBookings(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        
        Page<Booking> bookings = bookingRepository.findAll(PageRequest.of(page, size, sort));
        
        Map<String, Object> response = new HashMap<>();
        response.put("content", bookings.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList()));
        response.put("currentPage", bookings.getNumber());
        response.put("totalItems", bookings.getTotalElements());
        response.put("totalPages", bookings.getTotalPages());
        
        return response;
    }

    @Override
    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
        
        updateBookingFromDTO(booking, bookingDTO);
        Booking updatedBooking = bookingRepository.save(booking);
        return mapToDTO(updatedBooking);
    }

    @Override
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
        bookingRepository.delete(booking);
    }

    @Override
    public BookingDTO getBookingByReference(String reference) {
        Booking booking = bookingRepository.findByBookingNumber(reference);
        if (booking == null) {
            throw new ResourceNotFoundException("Booking", "reference", reference);
        }
        return mapToDTO(booking);
    }

    @Override
    public List<BookingDTO> getBookingsByUserEmail(String email) {
        return bookingRepository.findByUser_Email(email).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private BookingDTO mapToDTO(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .bookingNumber(booking.getBookingNumber())
                .userId(booking.getUser().getId())
                .userName(booking.getUser().getUsername())
                .travelPackageId(booking.getTravelPackage().getId())
                .travelPackageName(booking.getTravelPackage().getName())
                .availabilityId(booking.getAvailability().getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .numberOfTravelers(booking.getNumberOfTravelers())
                .totalAmount(booking.getTotalAmount())
                .contactEmail(booking.getContactEmail())
                .contactPhone(booking.getContactPhone())
                .specialRequests(booking.getSpecialRequests())
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }

    private Booking mapToEntity(BookingDTO bookingDTO) {
        return Booking.builder()
                .bookingNumber(generateBookingNumber())
                .startDate(bookingDTO.getStartDate())
                .endDate(bookingDTO.getEndDate())
                .numberOfTravelers(bookingDTO.getNumberOfTravelers())
                .totalAmount(bookingDTO.getTotalAmount())
                .contactEmail(bookingDTO.getContactEmail())
                .contactPhone(bookingDTO.getContactPhone())
                .specialRequests(bookingDTO.getSpecialRequests())
                .status(Booking.BookingStatus.PENDING)
                .build();
    }

    private void updateBookingFromDTO(Booking booking, BookingDTO bookingDTO) {
        booking.setStartDate(bookingDTO.getStartDate());
        booking.setEndDate(bookingDTO.getEndDate());
        booking.setNumberOfTravelers(bookingDTO.getNumberOfTravelers());
        booking.setTotalAmount(bookingDTO.getTotalAmount());
        booking.setContactEmail(bookingDTO.getContactEmail());
        booking.setContactPhone(bookingDTO.getContactPhone());
        booking.setSpecialRequests(bookingDTO.getSpecialRequests());
    }

    private String generateBookingNumber() {
        // Implementation of booking number generation
        // This could be a more sophisticated implementation
        return "BK" + System.currentTimeMillis();
    }
}
