package lk.ijse.ticketservice.service;

import lk.ijse.ticketservice.dto.TicketResReq;
import lk.ijse.ticketservice.dto.TicketResponse;

import java.util.List;

public interface TicketService {
    String registerTicket(TicketResponse ticketResponse) throws Exception;
    void updateTicket(Long id, TicketResReq ticketResponse) throws Exception;
    List<TicketResponse> getAllTickets();
    TicketResponse getTicketById(Long id) throws Exception;
    void updateTicketStatus(Long id);
    boolean isTicketExists(Long id);
}
