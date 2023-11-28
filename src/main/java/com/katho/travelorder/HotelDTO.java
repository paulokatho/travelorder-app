package com.katho.travelorder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {

    private Long id;
    private Long travelOrderId;
    private Integer nights;
}
