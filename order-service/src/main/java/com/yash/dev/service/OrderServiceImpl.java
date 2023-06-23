package com.yash.dev.service;

import com.yash.dev.entity.Order;
import com.yash.dev.exception.CustomException;
import com.yash.dev.external.client.PaymentService;
import com.yash.dev.external.client.ProductService;
import com.yash.dev.external.request.PaymentRequest;
import com.yash.dev.external.response.PaymentResponse;
import com.yash.dev.model.OrderRequest;
import com.yash.dev.model.OrderResponse;
import com.yash.dev.external.response.ProductResponse;
import com.yash.dev.repository.OrderRepository;
import java.time.Instant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yashwanthanands
 */

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private ProductService productService;

    private PaymentService paymentService;

    private RestTemplate restTemplate;

    @Autowired
    public OrderServiceImpl(ProductService prdService, OrderRepository orRepository,PaymentService payService,RestTemplate rtTemplate) {
        productService = prdService;
        orderRepository = orRepository;
        paymentService = payService;
        restTemplate = rtTemplate;
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

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get order details for Order ID : {} ",orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Order not found for the order id :"+orderId,
                        "NOT_FOUND",404));

        log.info("Invoking Product service to fetch the product for id : {} ",order.getProductId());

        ProductResponse productResponse = restTemplate.getForObject("http://PRODUCT-SERVICE/api/product/"+order.getProductId(),
                ProductResponse.class);

        log.info("Getting the payment information from the payment service");
        PaymentResponse paymentResponse = restTemplate.getForObject(
                "http://PAYMENT_SERVICE/api/payment/"+order.getProductId(),
                PaymentResponse.class
        );


        OrderResponse.ProductDetails productDetails =
                OrderResponse.ProductDetails.builder()
                        .productName(productResponse.getProductName())
                        .productId(productResponse.getProductId())
                        .build();

        OrderResponse.PaymentDetails paymentDetails
                = OrderResponse.PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentDate(paymentResponse.getPaymentDate())
                .orderId(paymentResponse.getOrderId())
                .status(paymentResponse.getStatus())
                .amount(paymentResponse.getAmount())
                .build();


        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();
        return orderResponse;
    }
}
