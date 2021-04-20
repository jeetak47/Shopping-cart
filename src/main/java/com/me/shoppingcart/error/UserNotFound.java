package com.me.shoppingcart.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFound extends ResponseStatusException {
    public UserNotFound() {
        super(HttpStatus.NOT_FOUND);
    }

    @Override
    public String getMessage() {
        return "User not found";
    }
}
