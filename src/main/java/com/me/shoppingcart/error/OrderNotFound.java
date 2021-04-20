package com.me.shoppingcart.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderNotFound extends ResponseStatusException {

    public OrderNotFound() {
        super(HttpStatus.NOT_FOUND);
    }

    @Override
    public String getMessage() {
        return "Order not found";
    }
}
