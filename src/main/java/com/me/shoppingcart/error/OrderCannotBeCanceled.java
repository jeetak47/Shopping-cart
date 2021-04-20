package com.me.shoppingcart.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderCannotBeCanceled extends ResponseStatusException {
    public OrderCannotBeCanceled() {
        super(HttpStatus.CONFLICT);
    }

    @Override
    public String getMessage() {
        return "Order Cannot Be Canceled";
    }
}
