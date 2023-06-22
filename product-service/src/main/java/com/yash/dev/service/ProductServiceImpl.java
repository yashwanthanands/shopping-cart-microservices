package com.yash.dev.service;

import com.yash.dev.entity.Product;
import com.yash.dev.exception.ProductServiceCustomException;
import com.yash.dev.model.ProductRequest;
import com.yash.dev.model.ProductResponse;
import com.yash.dev.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import static org.springframework.beans.BeanUtils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yashwanthanands
 */

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository prdRepository) {
        productRepository = prdRepository;
    }

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding Product ");
        Product product = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice()).build();
        productRepository.save(product);
        log.info("Product created successfully");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get the product for productId : {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("Product with given id is not found", "PRODUCT_NOT_FOUND"));
        ProductResponse productResponse = new ProductResponse();
        copyProperties(product, productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce Quantity {} for Id : {}", productId, quantity);
        Product product
                = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException(
                        "Product with given id not found",
                        "PRODUCT_NOT_FOUND"
                ));
        if (product.getQuantity() < quantity) {
            throw new ProductServiceCustomException(
                    "Product doesnot have sufficient quantity",
                    "INSUFFICIENT_QUANTITY"
            );
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity updated sucessfully");
    }
}

