package com.me.shoppingcart.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StockNotAvailable extends ResponseStatusException {
    public StockNotAvailable() {
        super(HttpStatus.CONFLICT);
    }

    @Override
    public String getMessage() {
        return "Stock not available";
    }
}
