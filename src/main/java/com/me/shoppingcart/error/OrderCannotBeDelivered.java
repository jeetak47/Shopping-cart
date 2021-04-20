package com.me.shoppingcart.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderCannotBeDelivered extends ResponseStatusException {
    public OrderCannotBeDelivered() {
        super(HttpStatus.CONFLICT);
    }

    @Override
    public String getMessage() {
        return "Order Cannot Be Delivered";
    }
}
