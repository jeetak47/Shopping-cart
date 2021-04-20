package com.me.shoppingcart.order;

import com.me.shoppingcart.customer.User;
import com.me.shoppingcart.error.ItemNotFoundInCart;

import java.util.*;

public class SimpleCart implements Cart{


    User user;
    Set<OrderItem> items =new HashSet<>();

    public SimpleCart(User user) {
        this.user = user;
    }

    @Override
    public void add(OrderItem item) {
        if(items.contains(item)){
            OrderItem orderItem = items.stream().filter(e -> e.equals(item)).findAny().get();
            Long total=orderItem.quantity()+item.quantity();
            items.add(new SimpleOrderItem(item.product(),total));
        }else {
            items.add(item);
        }

    }

    @Override
    public void remove(OrderItem item) {
        boolean contains = items.contains(item);
        if(contains){
            OrderItem orderItem = items.stream().filter(e -> e.equals(item)).findAny().get();
            Long total=orderItem.quantity()-item.quantity();
            contains=total>=0;
            if(contains){
                items.remove(item);
            }
           if(!contains){
               throw new ItemNotFoundInCart();
           }

        }
    }

    @Override
    public List<OrderItem> items() {
        return List.copyOf(items);
    }

    @Override
    public Double value() {
        return items.stream().mapToDouble(e->e.price()).sum();
    }

    @Override
    public User user() {
        return user;
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleCart that = (SimpleCart) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
