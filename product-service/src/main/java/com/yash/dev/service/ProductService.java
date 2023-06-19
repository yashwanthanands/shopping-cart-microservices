package com.yash.dev.service;

import com.yash.dev.model.ProductRequest;
import com.yash.dev.model.ProductResponse;

/**
 * @author yashwanthanands
 */
public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);
}
