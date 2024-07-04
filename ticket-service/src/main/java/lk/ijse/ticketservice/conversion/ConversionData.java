package lk.ijse.ticketservice.conversion;

import lk.ijse.ticketservice.dto.TicketResponse;
import lk.ijse.ticketservice.entity.TicketEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ConversionData {
    final private ModelMapper modelMapper;
    public TicketEntity convertToTicketEntity(TicketResponse ticketResponse){
        return modelMapper.map(ticketResponse, TicketEntity.class);
    }
    public List<TicketResponse> convertToTicketResponseList(List<TicketEntity> ticketEntities){
        return modelMapper.map(ticketEntities, List.class);
    }
    public TicketResponse convertToTicketResponse(TicketEntity ticket){
        return modelMapper.map(ticket, TicketResponse.class);
    }
}
