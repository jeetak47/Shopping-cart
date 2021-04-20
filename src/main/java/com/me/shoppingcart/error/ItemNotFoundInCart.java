package com.me.shoppingcart.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ItemNotFoundInCart extends ResponseStatusException {
    public ItemNotFoundInCart() {
        super(HttpStatus.NOT_FOUND);
    }

    @Override
    public String getMessage() {
        return "Item not found in cart";
    }
}
