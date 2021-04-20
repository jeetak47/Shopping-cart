package com.me.shoppingcart.order;

import com.me.shoppingcart.customer.User;

import java.util.List;

public interface OrderService {
    void placeOrder(User user, List<OrderItem> items);
    void cancelOrder(User user,String orderId);
    void returnOrder(User user,String orderId);
    void deliverOrder(User user,String orderId);
    List<Order> ordersByUser(User user);
    List<Order> getALL();
}
