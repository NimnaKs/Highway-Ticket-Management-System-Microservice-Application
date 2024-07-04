package lk.ijse.paymentservice.service;

import lk.ijse.paymentservice.dto.PaymentReqRes;

public interface PaymentService {
    String purchasePayment(PaymentReqRes paymentReqRes) throws Exception;

    PaymentReqRes getPaymentById(Long id);
}
