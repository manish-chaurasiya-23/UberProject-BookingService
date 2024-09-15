package com.example.UberProjectBookingService.services;

import com.example.UberProjectBookingService.dtos.CreateBookingDto;
import com.example.UberProjectBookingService.dtos.CreateBookingResponseDto;

public interface BookingService {
    CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails);
}
