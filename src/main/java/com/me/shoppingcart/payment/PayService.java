package com.me.shoppingcart.payment;

import com.me.shoppingcart.payment.PayAuth;

public interface PayService {
        boolean pay(Double amount, PayAuth auth);
}
