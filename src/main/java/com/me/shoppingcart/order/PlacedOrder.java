package com.me.shoppingcart.order;

import com.me.shoppingcart.customer.User;

import java.util.List;
import java.util.UUID;

public class PlacedOrder extends SimpleOrder {

    public PlacedOrder(User user,List<OrderItem> items) {
      super(UUID.randomUUID().toString(),user,items);
    }

    @Override
    public Order deliverOrder() {
        return new DeliveredOrder(this);
    }

    @Override
    public Order cancelOrder() {
        return new CanceledOrder(this);
    }

    @Override
    public OrderState state() {
        return OrderState.PLACED;
    }

}
