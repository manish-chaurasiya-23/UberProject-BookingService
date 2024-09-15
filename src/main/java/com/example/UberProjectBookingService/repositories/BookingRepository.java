package com.example.UberProjectBookingService.repositories;

import com.example.UberProjectEntityService.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}