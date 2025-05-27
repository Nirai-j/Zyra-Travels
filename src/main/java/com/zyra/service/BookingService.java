package com.zyra.service;

import com.zyra.dto.BookingDTO;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Map;

public interface BookingService {
    BookingDTO createBooking(BookingDTO bookingDTO);
    BookingDTO getBookingById(Long id);
    Map<String, Object> getAllBookings(int page, int size, String sortBy, String sortDir);
    BookingDTO updateBooking(Long id, BookingDTO bookingDTO);
    void cancelBooking(Long id);
    void deleteBooking(Long id);
    BookingDTO getBookingByReference(String reference);
    List<BookingDTO> getBookingsByUserEmail(String email);
}
