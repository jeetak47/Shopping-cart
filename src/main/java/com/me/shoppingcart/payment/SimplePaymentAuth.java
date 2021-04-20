package com.me.shoppingcart.payment;

public class SimplePaymentAuth implements PayAuth {
    String authKey;

    public SimplePaymentAuth(String authKey) {
        this.authKey = authKey;
    }

    public String getAuthKey() {
        return authKey;
    }

}
