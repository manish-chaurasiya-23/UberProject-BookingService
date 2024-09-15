package com.example.UberProjectBookingService.services;

import com.example.UberProjectBookingService.dtos.CreateBookingDto;
import com.example.UberProjectBookingService.dtos.CreateBookingResponseDto;
import com.example.UberProjectBookingService.dtos.DriverLocationDto;
import com.example.UberProjectBookingService.dtos.NearbyDriversRequestDto;
import com.example.UberProjectBookingService.repositories.BookingRepository;
import com.example.UberProjectBookingService.repositories.PassengerRepository;
import com.example.UberProjectEntityService.models.Booking;
import com.example.UberProjectEntityService.models.BookingStatus;
import com.example.UberProjectEntityService.models.Passenger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;

    private final RestTemplate restTemplate;

    private static final String LOCATION_SERVICE = "http://localhost:7777";

    public BookingServiceImpl(PassengerRepository passengerRepository,
                              BookingRepository bookingRepository) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = new RestTemplate();
    }


    @Override
    public CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails) {
        Optional<Passenger> passenger = passengerRepository.findById(bookingDetails.getPassengerId());
        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .startLocation(bookingDetails.getStartLocation())
                .passenger(passenger.get())
                .build();
        Booking newBooking = bookingRepository.save(booking);

        // make an api call to location service to fetch nearby drivers

        NearbyDriversRequestDto request = NearbyDriversRequestDto.builder()
                .latitude(bookingDetails.getStartLocation().getLatitude())
                .longitude(bookingDetails.getStartLocation().getLongitude())
                .build();

        ResponseEntity<DriverLocationDto[]> result = restTemplate.postForEntity(LOCATION_SERVICE + "/api/location/nearby/drivers", request, DriverLocationDto[].class);

        if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
            List<DriverLocationDto> driverLocations = Arrays.asList(result.getBody());
            driverLocations.forEach(driverLocationDto -> {
                System.out.println(driverLocationDto.getDriverId() + " " + "lat: " + driverLocationDto.getLatitude() + "long: " + driverLocationDto.getLongitude());
            });
        }

        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().toString())
                .build();
    }
}
