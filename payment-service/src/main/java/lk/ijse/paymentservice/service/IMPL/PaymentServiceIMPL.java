package lk.ijse.paymentservice.service.IMPL;

import lk.ijse.paymentservice.client.TicketServiceClient;
import lk.ijse.paymentservice.conversion.ConversionData;
import lk.ijse.paymentservice.dto.PaymentReqRes;
import lk.ijse.paymentservice.entity.PaymentEntity;
import lk.ijse.paymentservice.exception.NotFoundException;
import lk.ijse.paymentservice.repository.PaymentRepository;
import lk.ijse.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceIMPL implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ConversionData conversionData;
    private final TicketServiceClient ticketServiceClient;

    @Override
    public String purchasePayment(PaymentReqRes paymentReqRes) throws Exception {
        try {
            if (!ticketServiceClient.isTicketExists(paymentReqRes.getTicketId())) {
                throw new NotFoundException("Ticket Not Exists");
            }
            PaymentEntity paymentEntity = conversionData.convertToPaymentEntity(paymentReqRes);
            paymentEntity.setDateTime(LocalDateTime.now());
            PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntity);
            ticketServiceClient.updateTicketStatus(paymentReqRes.getTicketId());
            return "Payment Done Successfully with ID: " + savedPaymentEntity.getId();
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("Payment Failed");
        }
    }

    @Override
    public PaymentReqRes getPaymentById(Long id) {
        Optional<PaymentEntity> paymentRepositoryById = paymentRepository.findById(id);
        if (paymentRepositoryById.isEmpty())
            throw new NotFoundException("Payment Details Not Found.");
        return conversionData.convertToPaymentReqRes(paymentRepositoryById.get());
    }
}