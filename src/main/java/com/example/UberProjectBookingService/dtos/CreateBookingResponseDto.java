package com.example.UberProjectBookingService.dtos;

import com.example.UberProjectEntityService.models.Driver;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingResponseDto {

    private long bookingId;

    private String bookingStatus;

    private Optional<Driver> driver;

}