package com.me.shoppingcart.order;

import com.me.shoppingcart.customer.User;
import com.me.shoppingcart.error.OrderCannotBeCanceled;

import java.util.List;

public class DeliveredOrder extends SimpleOrder {

    public DeliveredOrder(Order order) {
        super(order);
    }

    @Override
    public Order returnOrder() {
        return new ReturnedOrder(this);
    }

    @Override
    public OrderState state() {
        return OrderState.DELIVERED;
    }

}
