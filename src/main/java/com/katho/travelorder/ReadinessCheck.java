package com.katho.travelorder;

import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Readiness
public class ReadinessCheck implements HealthCheck {

    @RestClient
    @Inject
    FlightService flightService;

    @RestClient
    @Inject
    HotelService hotelService;

    @Override
    public HealthCheckResponse call() {
        List<TravelOrderDTO> ordersList = ordersList = TravelOrder.<TravelOrder>listAll().stream()
                .map(
                        order -> TravelOrderDTO.of(
                                order,
                                flightService.findByTravelOrderId(order.id),
                                hotelService.findByTravelOrderId(order.id)
                        )
                ).collect(Collectors.toList());

        if (ordersList.contains(ordersList) || ordersList.isEmpty()) {
            return HealthCheckResponse.up("Estou vivo! - ReadinessCheck");
        } else {
            return HealthCheckResponse.down("NÃO estou vivo! - ReadinessCheck");
        }
    }
}
