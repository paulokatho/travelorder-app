package com.katho.travelorder;

import lombok.Data;

@Data
public class TravelOrderDTO {

    private String fromAirPort;
    private String toAirPort;
    private Integer nights;

    public TravelOrderDTO() {

    }
    public TravelOrderDTO(String fromAirPort, String toAirPort, Integer nights) {
        this.fromAirPort = fromAirPort;
        this.toAirPort = toAirPort;
        this.nights = nights;
    }

    public static TravelOrderDTO of(TravelOrder order, FlightDTO flight, HotelDTO hotel) {
        if (flight == null) {
            flight = new FlightDTO();
        }
        if (hotel == null) {
            hotel = new HotelDTO();
        }
        return new TravelOrderDTO(flight.getFromAirPort(), flight.getToAirPort(), hotel.getNights());
    }

    public static TravelOrderDTO of(String fromAirPort, String toAirPort, Integer nights) {
        return new TravelOrderDTO(fromAirPort, toAirPort, nights);
    }
}
