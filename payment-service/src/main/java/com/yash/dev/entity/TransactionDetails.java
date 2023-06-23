package com.yash.dev.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yashwanthanands
 */

@Entity
@Table(name="transaction_details")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="order_id")
    private long orderId;

    @Column(name="payment_mode")
    private String paymentMode;

    @Column(name="reference_number")
    private String referenceNumber;

    @Column(name="payment_date")
    private Instant payment_date;

    @Column(name="payment_status")
    private String paymentStatus;

    @Column(name="amount")
    private long amount;
}
