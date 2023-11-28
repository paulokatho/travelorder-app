package com.katho.travelorder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {

    private Long id;
    private Long travelOrderId;
    private String fromAirPort;
    private String toAirPort;
}
