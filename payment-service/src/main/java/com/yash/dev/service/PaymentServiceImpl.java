package com.yash.dev.service;

import com.yash.dev.entity.TransactionDetails;
import com.yash.dev.model.PaymentRequest;
import com.yash.dev.repository.TransactionDetailsRepository;
import java.time.Instant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yashwanthanands
 */

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    private TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    public PaymentServiceImpl(TransactionDetailsRepository transRepo) {
        transactionDetailsRepository=transRepo;
    }
    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording Payment Details : {} ",paymentRequest);
        TransactionDetails transactionDetails=TransactionDetails.builder()
                .payment_date(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .referenceNumber(paymentRequest.getReferenceNumber())
                .orderId(paymentRequest.getOrderId())
                .amount(paymentRequest.getAmount())
                .build();
        transactionDetailsRepository.save(transactionDetails);
        log.info("Transaction Completed with ID : {} ",transactionDetails.getId());
        return transactionDetails.getId();
    }
}
