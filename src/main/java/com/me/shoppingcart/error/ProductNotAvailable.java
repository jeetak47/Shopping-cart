package com.me.shoppingcart.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductNotAvailable extends ResponseStatusException {
    public ProductNotAvailable() {
        super(HttpStatus.NOT_FOUND);
    }

    @Override
    public String getMessage() {
        return "Product not found";
    }
}
