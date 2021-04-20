package com.me.shoppingcart.order;

import com.me.shoppingcart.customer.User;
import com.me.shoppingcart.error.OrderNotFound;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    Set<Order> orders = new HashSet<>();

    @Override
    public void placeOrder(User user, List<OrderItem> items) {
        Order order= new PlacedOrder(user,items);
        orders.add(order);
    }

    @Override
    public void cancelOrder(User user, String orderId) {
        Optional<Order> order = orders.stream().filter(e -> e.id().equals(orderId)).findAny();
        if(order.isPresent() && order.get().user().equals(user)){
            orders.remove(order.get());
            orders.add(new CanceledOrder(order.get()));
        }else {
            throw  new OrderNotFound();
        }
    }

    @Override
    public void returnOrder(User user, String orderId) {
        Optional<Order> order = orders.stream().filter(e -> e.id().equals(orderId)).findAny();
        if(order.isPresent() && order.get().user().equals(user)){
            orders.remove(order.get());
            orders.add(new ReturnedOrder(order.get()));
        }else {
            throw  new OrderNotFound();
        }
    }

    @Override
    public void deliverOrder(User user, String orderId) {
        Optional<Order> order = orders.stream().filter(e -> e.id().equals(orderId)).findAny();
        if(order.isPresent()){
            orders.remove(order.get());
            orders.add(new DeliveredOrder(order.get()));
        }else {
            throw  new OrderNotFound();
        }
    }

    @Override
    public List<Order> ordersByUser(User user) {
        return orders.stream().filter(e->e.user().equals(user)).collect(Collectors.toList());
    }

    @Override
    public List<Order> getALL() {
        return List.copyOf(orders);
    }


}
