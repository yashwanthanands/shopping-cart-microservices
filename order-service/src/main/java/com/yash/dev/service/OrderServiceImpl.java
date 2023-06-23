package com.yash.dev.service;

import com.yash.dev.entity.Order;
import com.yash.dev.external.client.PaymentService;
import com.yash.dev.external.client.ProductService;
import com.yash.dev.external.request.PaymentRequest;
import com.yash.dev.model.OrderRequest;
import com.yash.dev.repository.OrderRepository;
import java.time.Instant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yashwanthanands
 */

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private ProductService productService;

    private PaymentService paymentService;

    @Autowired
    public OrderServiceImpl(ProductService prdService, OrderRepository orRepository,PaymentService payService) {
        productService = prdService;
        orderRepository = orRepository;
        paymentService = payService;
    }

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //Order Entity -> Save the data with Status Order Created
        //Product Service -> Block Products (Reduce the Quantity)
        //Payment Service -> Complete the Payment -> If Success-> COMPLETE OR CANCEL for failure

        log.info("Placing Order Request: {}", orderRequest);
        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());
        log.info("Creating Order with Status Created");
        Order order= Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
        order = orderRepository.save(order);

        log.info("Calling Payment Service to complete the order request");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();
        String orderStatus = null;
                try {
                    paymentService.doPayment(paymentRequest);
                    log.info("Payment done successfully. Changing the order status to PLACED");
                    orderStatus = "PLACED";
                }catch(Exception e) {
                    log.error("Error occured in payment. Chaning the order status to FAILED");
                    orderStatus = "PAYMENT_FAILED";
                }
                order.setOrderStatus(orderStatus);
                orderRepository.save(order);

        log.info("Order Placed successfully with Order ID : {}",order.getId());
        return order.getId();
    }
}
