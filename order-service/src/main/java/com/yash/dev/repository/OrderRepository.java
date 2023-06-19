package com.yash.dev.repository;

import com.yash.dev.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yashwanthanands
 */
public interface OrderRepository extends JpaRepository<Order,Long> {
}
