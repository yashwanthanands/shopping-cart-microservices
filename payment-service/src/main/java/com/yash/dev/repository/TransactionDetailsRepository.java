package com.yash.dev.repository;

import com.yash.dev.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yashwanthanands
 */

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails,Long> {
}
