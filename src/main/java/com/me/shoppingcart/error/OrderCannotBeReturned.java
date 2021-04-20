package com.me.shoppingcart.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderCannotBeReturned extends ResponseStatusException {
    public OrderCannotBeReturned() {
        super(HttpStatus.CONFLICT);
    }

    @Override
    public String getMessage() {
        return "Order Cannot Be Returned";
    }
}
