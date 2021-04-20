package com.me.shoppingcart.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CartIsEmpty extends ResponseStatusException {
    public CartIsEmpty() {
        super(HttpStatus.CONFLICT);
    }

    @Override
    public String getMessage() {
        return "Cart is Empty";
    }
}
