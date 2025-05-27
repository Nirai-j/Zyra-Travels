package com.zyra.repository;

import com.zyra.model.Booking;
import com.zyra.model.Booking.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByUser_Email(String email);
    List<Booking> findByTravelPackageId(Long travelPackageId);
    List<Booking> findByStatus(BookingStatus status);
    Booking findByBookingNumber(String bookingNumber);
}
