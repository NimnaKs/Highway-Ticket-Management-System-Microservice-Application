package lk.ijse.ticketservice.service.IMPL;

import lk.ijse.ticketservice.Enum.Status;
import lk.ijse.ticketservice.client.UserServiceClient;
import lk.ijse.ticketservice.client.VehicleServiceClient;
import lk.ijse.ticketservice.conversion.ConversionData;
import lk.ijse.ticketservice.dto.TicketResReq;
import lk.ijse.ticketservice.dto.TicketResponse;
import lk.ijse.ticketservice.entity.TicketEntity;
import lk.ijse.ticketservice.exception.NotFoundException;
import lk.ijse.ticketservice.repository.TicketRepository;
import lk.ijse.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceIMPL implements TicketService {

    private final VehicleServiceClient vehicleServiceClient;
    private final UserServiceClient userServiceClient;
    private final TicketRepository ticketRepository;
    private final ConversionData conversionData;

    @Override
    public String registerTicket(TicketResponse ticket) throws Exception {
        try {
            if (!vehicleServiceClient.isVehicleExists(ticket.getVehicleId())) {
                throw new NotFoundException("Vehicle Not Exists");
            }
            if (!userServiceClient.isUserExists(ticket.getUserId()))
                throw new NotFoundException("User Not Exists");
            TicketEntity ticket1 = conversionData.convertToTicketEntity(ticket);
            ticket1.setStatus(Status.UNPAID);
            ticket1.setIssueDate(LocalDateTime.now());
            TicketEntity ticketEntity = ticketRepository.save(ticket1);
            return "Ticket Registered Successfully with ID: " + ticketEntity.getId();
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("Ticket Registration Failed");
        }
    }

    @Override
    public void updateTicket(Long id, TicketResReq ticketResReq) throws Exception {
        try {
            if (!vehicleServiceClient.isVehicleExists(ticketResReq.getVehicleId())) {
                throw new NotFoundException("Vehicle Not Exists");
            }
            if (!userServiceClient.isUserExists(ticketResReq.getUserId()))
                throw new NotFoundException("User Not Exists");
            Optional<TicketEntity> ticketEntityOptional = ticketRepository.findById(id);
            if (ticketEntityOptional.isEmpty()) {
                throw new NotFoundException("Ticket Not Exists");
            }
            TicketEntity ticketEntity = ticketEntityOptional.get();
            ticketEntity.setStatus(ticketResReq.getStatus());
            ticketEntity.setVehicleId(ticketResReq.getVehicleId());
            ticketEntity.setUserId(ticketResReq.getUserId());
            ticketRepository.save(ticketEntity);
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("Ticket Update Failed");
        }
    }

    @Override
    public List<TicketResponse> getAllTickets() {
        return conversionData.convertToTicketResponseList(ticketRepository.findAll());
    }

    @Override
    public TicketResponse getTicketById(Long id) throws Exception {
        Optional<TicketEntity> ticketEntityOptional = ticketRepository.findById(id);
        if (ticketEntityOptional.isEmpty()) {
            throw new NotFoundException("Ticket Not Exists");
        }
        return conversionData.convertToTicketResponse(ticketEntityOptional.get());
    }
}