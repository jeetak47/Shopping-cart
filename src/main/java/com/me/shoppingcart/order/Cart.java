package com.me.shoppingcart.order;

import com.me.shoppingcart.customer.User;

import java.util.List;

public interface Cart {
    void add(OrderItem item);
    void remove(OrderItem item);
    List<OrderItem> items();
    Double value();
    User user();
    void clear();
}
