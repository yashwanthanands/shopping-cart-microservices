package com.yash.dev.model;

import lombok.Data;

/**
 * @author yashwanthanands
 */

@Data
public class ProductRequest {
    private String name;
    private long price;
    private long quantity;
}
