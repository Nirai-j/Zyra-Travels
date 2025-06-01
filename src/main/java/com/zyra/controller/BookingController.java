package com.zyra.controller;

import com.zyra.dto.BookingDTO;
import com.zyra.service.BookingService;
import com.zyra.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAllBookings(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDir) {
        
        return ResponseEntity.ok(bookingService.getAllBookings(page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id, Principal principal) {
        BookingDTO booking = bookingService.getBookingById(id);
        
        // Only allow admin or the booking owner to access
        if (!principal.getName().equals(booking.getUserEmail()) && 
            !isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/reference/{reference}")
    public ResponseEntity<BookingDTO> getBookingByReference(@PathVariable String reference, Principal principal) {
        BookingDTO booking = bookingService.getBookingByReference(reference);
        
        // Only allow admin or the booking owner to access
        if (!principal.getName().equals(booking.getUserEmail()) && 
            !isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingDTO>> getMyBookings(Principal principal) {
        return ResponseEntity.ok(bookingService.getBookingsByUserEmail(principal.getName()));
    }

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingDTO bookingDTO, Principal principal) {
        // Ensure the user can only book for themselves
        bookingDTO.setUserEmail(principal.getName());
        return new ResponseEntity<>(bookingService.createBooking(bookingDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, 
                                                  @Valid @RequestBody BookingDTO bookingDTO,
                                                  Principal principal) {
        
        BookingDTO existingBooking = bookingService.getBookingById(id);
        
        // Only allow admin or the booking owner to update
        if (!principal.getName().equals(existingBooking.getUserEmail()) && 
            !isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDTO));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id, Principal principal) {
        BookingDTO existingBooking = bookingService.getBookingById(id);
        
        // Only allow admin or the booking owner to cancel
        if (!principal.getName().equals(existingBooking.getUserEmail()) && 
            !isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
    
    // Helper method to check if user has admin role
    private boolean isAdmin(Principal principal) {
        // Implementation would depend on how roles are stored and accessed
        // For simplicity, assuming there's a service method to check this
        // return userService.hasAdminRole(principal.getName());
        return true; // Placeholder - replace with actual implementation
    }
}