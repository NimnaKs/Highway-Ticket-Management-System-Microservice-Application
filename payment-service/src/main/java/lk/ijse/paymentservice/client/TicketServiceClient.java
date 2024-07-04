package lk.ijse.paymentservice.client;

public interface TicketServiceClient {
    boolean isTicketExists(Long ticketId);
    void updateTicketStatus(Long ticketId);
}
