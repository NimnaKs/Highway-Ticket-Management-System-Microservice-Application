package lk.ijse.ticketservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lk.ijse.ticketservice.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketResReq {
    @Null(message = "Auto generated by program")
    private Long id;
    @Null(message = "Ticket issue date is added by program")
    private LocalDateTime issueDate;
    @NotNull (message = "Ticket status is required")
    private Status status;
    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;
    @NotNull(message = "User ID is required")
    private Long userId;
}