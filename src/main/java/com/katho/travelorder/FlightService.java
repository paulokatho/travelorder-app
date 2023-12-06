package com.katho.travelorder;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.time.temporal.ChronoUnit;
import java.util.List;

//@RegisterRestClient(baseUri = "http://localhost:8081/flight")
@RegisterRestClient(baseUri = "http://flight-app-katho-mau-dev.apps.sandbox-m3.1530.p1.openshiftapps.com/flight")
public interface FlightService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FlightDTO> FlightDTOs();

    @GET
    @Path("findById")
    @Produces(MediaType.APPLICATION_JSON)
    public FlightDTO findById(@QueryParam("id") long id);

    @GET
    @Path("findByTravelOrderId")
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout(unit = ChronoUnit.SECONDS, value = 2)
    @Fallback(fallbackMethod = "fallback")
    public FlightDTO findByTravelOrderId(@QueryParam("travelOrderId") long travelOrderId);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public FlightDTO newFlightDTO(FlightDTO flightDTO);

    default FlightDTO fallback(long travelOrderId) {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setToAirPort("");
        flightDTO.setFromAirPort("");
        flightDTO.setTravelOrderId(travelOrderId);

        System.out.println(" Flight - FALL BACK ....");

        return flightDTO;

        //PODE-SE HAVER UMA VALIDAÇÃO NO FRONT QUE QUANDO VOLTA -1 É PQ A APLICAÇÃO ESTÁ COM PROBLEMAS, POR ISSO
        //COLOCAMOS -1 NO RETORNO DO OBJETO flightDTO
    }
}
