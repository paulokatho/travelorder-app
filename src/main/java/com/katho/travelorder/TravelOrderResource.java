package com.katho.travelorder;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Path("travelOrders")
public class TravelOrderResource {

    @Inject
    @RestClient
    HotelService hotelService;

    @Inject
    @RestClient
    FlightService flightService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TravelOrderDTO> orders() {
        return TravelOrder.<TravelOrder>listAll().stream()
                .map(
                        order -> TravelOrderDTO.of(
                                order,
                                flightService.findByTravelOrderId(order.id),
                                hotelService.findByTravelOrderId(order.id)
                        )
                ).collect(Collectors.toList());
    }

    @GET
    @Path("findById")
    public TravelOrder findById(@QueryParam("id") long id) {
        return TravelOrder.findById(id);
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TravelOrder newTravelOrder(TravelOrderDTO dto) {
        TravelOrder order = new TravelOrder();
        order.id = null;
        order.persist();

        FlightDTO flight = new FlightDTO();
        flight.setFromAirPort(dto.getFromAirPort());
        flight.setToAirPort(dto.getToAirPort());
        flight.setTravelOrderId(order.id);
        flightService.newFlightDTO(flight);

        HotelDTO hotel = new HotelDTO();
        hotel.setNights(dto.getNights());
        hotel.setTravelOrderId(order.id);
        hotelService.newHotel(hotel);

        return order;
    }
}
