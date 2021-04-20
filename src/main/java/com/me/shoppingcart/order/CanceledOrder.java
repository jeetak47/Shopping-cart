package com.me.shoppingcart.order;

import com.me.shoppingcart.customer.User;
import com.me.shoppingcart.error.OrderCannotBeCanceled;
import com.me.shoppingcart.error.OrderCannotBeDelivered;
import com.me.shoppingcart.error.OrderCannotBeReturned;

import java.util.List;

public class CanceledOrder extends SimpleOrder {

    public CanceledOrder(Order order) {
        super(order);
    }

    @Override
    public OrderState state() {
        return OrderState.CANCELED;
    }

}
