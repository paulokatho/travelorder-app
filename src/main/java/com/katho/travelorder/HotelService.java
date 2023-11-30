package com.katho.travelorder;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.time.temporal.ChronoUnit;
import java.util.List;

@RegisterRestClient(baseUri = "http://localhost:8082/hotel")
public interface HotelService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HotelDTO> listAll();

    @GET
    @Path("findById")
    @Produces(MediaType.APPLICATION_JSON)
    public HotelDTO findById(@QueryParam("id") long id);

    @GET
    @Path("findByTravelOrderId")
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout(unit = ChronoUnit.SECONDS, value = 2)
    @Fallback(fallbackMethod = "fallback")
    @CircuitBreaker(
            requestVolumeThreshold = 4,
            failureRatio = 0.5,
            delay = 5000,
            successThreshold = 2
    )
    public HotelDTO findByTravelOrderId(@QueryParam("travelOrderId") long travelOrderId);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public HotelDTO newHotel(HotelDTO hotel);

    default HotelDTO fallback(long travelOrderId) {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setNights(-1);

        System.out.println("FALL BACK HOTEL....");

        return hotelDTO;

        //PODE-SE HAVER UMA VALIDAÇÃO NO FRONT QUE QUANDO VOLTA -1 É PQ A APLICAÇÃO ESTÁ COM PROBLEMAS, POR ISSO
        //COLOCAMOS -1 NO RETORNO DO OBJETO flightDTO
    }

}
