package com.yash.dev.repository;

import com.yash.dev.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yashwanthanands
 */

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
