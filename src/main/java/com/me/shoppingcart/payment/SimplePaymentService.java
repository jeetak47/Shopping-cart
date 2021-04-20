package com.me.shoppingcart.payment;

public class SimplePaymentService implements PayService{
    @Override
    public boolean pay(Double amount, PayAuth auth) {
        return true;
    }
}
