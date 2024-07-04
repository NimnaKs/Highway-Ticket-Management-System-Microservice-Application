package lk.ijse.paymentservice.conversion;

import lk.ijse.paymentservice.dto.PaymentReqRes;
import lk.ijse.paymentservice.entity.PaymentEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConversionData {
    final private ModelMapper modelMapper;

    public PaymentEntity convertToPaymentEntity(PaymentReqRes paymentReqRes) {
        return modelMapper.map(paymentReqRes, PaymentEntity.class);
    }

    public PaymentReqRes convertToPaymentReqRes(PaymentEntity paymentRepositoryById) {
        return modelMapper.map(paymentRepositoryById, PaymentReqRes.class);
    }
}
