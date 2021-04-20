package com.me.shoppingcart.order;

import com.me.shoppingcart.customer.User;

import java.util.List;

public interface Order {
    String id();
    User user();
    Order deliverOrder();
    Order cancelOrder();
    Order returnOrder();
    default Double value(){
        return orderItems().stream().mapToDouble(e->e.price()*e.quantity()).sum();
    }
    OrderState state();
    List<OrderItem> orderItems();
    default Double getTotal(){
        return orderItems().stream().mapToDouble(e->e.price()).sum();
    }
     enum OrderState{
         PLACED,
         DELIVERED,
         CANCELED,
         RETURNED,
     }
}
