package lk.ijse.ticketservice.client.IMPL;

import lk.ijse.ticketservice.client.VehicleServiceClient;
import lk.ijse.ticketservice.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class VehicleServiceClientIMPL implements VehicleServiceClient {

    private final RestTemplate restTemplate;

    @Override
    public boolean isVehicleExists(Long vehicleId) {
        String url = "http://localhost:8080/api/v1/vehicle/" + vehicleId;
        try {
            Boolean response = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(response);
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NotFoundException("Vehicle not found");
        }
    }
}
