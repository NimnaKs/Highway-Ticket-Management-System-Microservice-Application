package lk.ijse.paymentservice.client.IMPL;

import lk.ijse.paymentservice.client.TicketServiceClient;
import lk.ijse.paymentservice.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TicketServiceClientIMPL implements TicketServiceClient {

    private final RestTemplate restTemplate;

    @Override
    public boolean isTicketExists(Long ticketId) {
        String url = "http://localhost:8080/api/v1/ticket/isTicketExists/" + ticketId;

        try {
            Boolean response = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(response);
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NotFoundException("Url Not Found");
        }
    }

    @Override
    public void updateTicketStatus(Long ticketId) {
        String url = "http://localhost:8080/api/v1/ticket/updateStatus/" + ticketId;

        try {
            restTemplate.put(url,null);
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NotFoundException("Url Not Found");
        }
    }
}
