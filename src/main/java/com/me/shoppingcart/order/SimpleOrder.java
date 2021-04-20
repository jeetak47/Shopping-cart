package com.me.shoppingcart.order;

import com.me.shoppingcart.customer.User;
import com.me.shoppingcart.error.OrderCannotBeCanceled;
import com.me.shoppingcart.error.OrderCannotBeDelivered;
import com.me.shoppingcart.error.OrderCannotBeReturned;

import java.util.List;
import java.util.Objects;

public abstract class SimpleOrder implements Order{
    protected final List<OrderItem> items;
    private final String id;
    private final User user;

    public SimpleOrder(String id,User user,List<OrderItem> items) {
        this.id=id;
        this.user=user;
        this.items = items;

    }
    public SimpleOrder(Order order){
        this.id=order.id();
        this.user=order.user();
        this.items=order.orderItems();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public User user() {
        return user;
    }

    @Override
    public Order deliverOrder() {
        throw  new OrderCannotBeDelivered();
    }

    @Override
    public Order cancelOrder() {
        throw  new OrderCannotBeCanceled();
    }

    @Override
    public Order returnOrder() {
        throw new OrderCannotBeReturned();
    }

    @Override
    public List<OrderItem> orderItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleOrder that = (SimpleOrder) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
